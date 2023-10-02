
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.json.JSONException;
import org.json.JSONObject;


public class CryptoProducer {
	public static void main(String[] args) {
        String kafkaTopic = "coinmarketcap-topic";
        String kafkaBootstrapServers = "localhost:9092";
        String coinMarketCapApiKey = "b1af563b-1c31-46d7-8b35-214ffc2e94c5";
        
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBootstrapServers);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new KafkaProducer<>(properties);
        
        while (true) {
            try {
                JSONObject coinData = fetchCoinMarketCapData(coinMarketCapApiKey);
                if (coinData != null) {
                    String jsonData = coinData.toString();
                    ProducerRecord<String, String> record = new ProducerRecord<>(kafkaTopic, jsonData);
                    producer.send(record);
                    System.out.println("Published data to Kafka: " + jsonData);
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
            // Sleep for a while
            try {
                Thread.sleep(10000); // Sleep for 10 seconds
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
	}
	
	public static JSONObject fetchCoinMarketCapData(String apiKey) throws JSONException {
        String apiUrl = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest";
        String request = apiUrl + "?start=1&limit=100&sort=market_cap&cryptocurrency_type=all&tag=all";
        try {
            URL url = new URL(request);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("X-CMC_PRO_API_KEY", apiKey);
            
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }

                in.close();
                return new JSONObject(response.toString());
            } else {
                System.out.println("HTTP GET request failed with response code: " + responseCode);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
	}
	
}
