import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.TopicPartition;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaInputDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka010.ConsumerStrategies;
import org.apache.spark.streaming.kafka010.KafkaUtils;
import org.apache.spark.streaming.kafka010.LocationStrategies;

import scala.Tuple2;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Main {

    private static final String TABLE_NAME = "cryptomarketdata";
    private static final boolean deleteTable = false;

    // to check if table has been created and initialized so only update happens
    // after first RDD create a table
    private static int tableInitialized = 0;

    public static void putDataIntoHBase(String cryptoName, double averagePrice) throws IOException {
        Configuration hbaseConfig = HBaseConfiguration.create();
        try (Connection connection = ConnectionFactory.createConnection(hbaseConfig);
                Admin admin = connection.getAdmin()) {
            TableName tableName = TableName.valueOf(TABLE_NAME);

            if (tableInitialized == 0 && deleteTable) {
                admin.disableTable(tableName);
                admin.deleteTable(tableName);
            }

            if (!admin.tableExists(tableName)) {
                tableInitialized++;
                HTableDescriptor table = new HTableDescriptor(tableName);
                table.addFamily(new HColumnDescriptor("cf"));

                System.out.println("Creating table....");
                admin.createTable(table);
                System.out.println("Done");
            } else if (tableInitialized == 0) {
                System.out.println("Table Exist.....");
            }

            Table t = connection.getTable(tableName);
            long timestamp = System.currentTimeMillis();
            Put put = new Put(Bytes.toBytes(cryptoName));

            // set average price column with timestamp to store price history
            put.addColumn(Bytes.toBytes("cf"), Bytes.toBytes("AveragePrice:" + timestamp),
                    Bytes.toBytes(String.valueOf(averagePrice)));
            t.put(put);
        }
    }

    public static void main(String[] args) throws IOException {
        // Set up Spark Streaming configuration
        SparkConf sparkConf = new SparkConf().setAppName("KafkaSparkStreamingConsumer").setMaster("local[*]");
        JavaStreamingContext streamingContext = new JavaStreamingContext(sparkConf, Durations.seconds(10));

        // checkpoint for window streaming
        streamingContext.checkpoint("hdfs://localhost:8020/cloudera/cloudera/spark_checkpoint/");

        // Kafka configuration
        String kafkaBrokers = "localhost:9092";
        String kafkaTopic = "coinmarketcap-topic";

        // Kafka consumer configuration
        Map<String, Object> kafkaParams = new HashMap<>();
        kafkaParams.put("bootstrap.servers", kafkaBrokers);
        kafkaParams.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        kafkaParams.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        kafkaParams.put("group.id", "spark-streaming-consumer-group");

        // Create Kafka direct stream
        JavaInputDStream<ConsumerRecord<String, String>> kafkaStream = KafkaUtils.createDirectStream(
                streamingContext,
                LocationStrategies.PreferConsistent(),
                ConsumerStrategies.<String, String>Subscribe(Collections.singletonList(kafkaTopic), kafkaParams));

        // Extract the value (JSON data) from Kafka records
        JavaDStream<String> jsonDataStream = kafkaStream.map(ConsumerRecord::value);

        // Parse the JSON data into Java objects
        JavaDStream<CoinMarketData> coinMarketDataStream = jsonDataStream.map(jsonData -> {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(jsonData, CryptoDataResponse.class);
        }).flatMap(d -> d.getData().iterator());

        // Create a pair DStream with cryptocurrency name as key and a tuple of price
        // and count as value
        JavaPairDStream<String, Tuple2<Double, Integer>> pairDStream = coinMarketDataStream.mapToPair(
                data -> new Tuple2<>(data.getSymbol(), new Tuple2<>(data.getQuote().getUsd().getPrice(), 1)));

        // Use the reduceByKeyAndWindow transformation to accumulate values and count
        JavaPairDStream<String, Tuple2<Double, Integer>> reducedStream = pairDStream.reduceByKeyAndWindow(
                (v1, v2) -> new Tuple2<>(v1._1() + v2._1(), v1._2() + v2._2()),
                (v1, v2) -> new Tuple2<>(v1._1() - v2._1(), v1._2() - v2._2()),
                Durations.seconds(60),
                Durations.seconds(60));

        // Calculate the average for each cryptocurrency
        JavaPairDStream<String, Double> averageStream = reducedStream.mapValues(value -> value._1() / value._2());

        // store the average price for each cryptocurrency into hbase with current
        // timestamp
        averageStream.foreachRDD(rdd -> {
            rdd.foreach(tuple -> {
                String cryptoName = tuple._1();
                double averagePrice = tuple._2();
                putDataIntoHBase(cryptoName, averagePrice);
            });
        });

        // Start the Spark Streaming context
        streamingContext.start();

        // Wait for the termination of the Spark Streaming context
        try {
            streamingContext.awaitTermination();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
