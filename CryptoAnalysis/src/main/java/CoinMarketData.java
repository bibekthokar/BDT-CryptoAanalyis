import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CoinMarketData {
    @JsonProperty("symbol")
    private String symbol;

    @JsonProperty("circulating_supply")
    private double circulatingSupply;

    @JsonProperty("last_updated")
    private String lastUpdated;

    @JsonProperty("total_supply")
    private long totalSupply;
    
    @JsonProperty("cmc_rank")
    private int cmcRank;

    @JsonProperty("tags")
    private List<String> tags;

    @JsonProperty("date_added")
    private String dateAdded;

    @JsonProperty("quote")
    private QuoteData quote;

    @JsonProperty("num_market_pairs")
    private int numMarketPairs;
    
    @JsonProperty("infinite_supply")
    private boolean infiniteSupply;

    @JsonProperty("name")
    private String name;

    @JsonProperty("max_supply")
    private long maxSupply;

    @JsonProperty("id")
    private int id;

    @JsonProperty("slug")
    private String slug;
    
    
    

    @Override
	public String toString() {
		return "CoinMarketData [symbol=" + symbol + ", circulatingSupply="
				+ circulatingSupply + ", lastUpdated=" + lastUpdated
				+ ", totalSupply=" + totalSupply + ", cmcRank=" + cmcRank
				+ ", tags=" + tags + ", dateAdded=" + dateAdded + ", quote="
				+ quote + ", numMarketPairs=" + numMarketPairs
				+ ", infiniteSupply=" + infiniteSupply + ", name=" + name
				+ ", maxSupply=" + maxSupply + ", id=" + id + ", slug=" + slug
				+ "]";
	}

	// Getters and setters
    public String getSymbol() {
        return symbol;
    }
    
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getCirculatingSupply() {
        return circulatingSupply;
    }

    public void setCirculatingSupply(double circulatingSupply) {
        this.circulatingSupply = circulatingSupply;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
    public long getTotalSupply() {
        return totalSupply;
    }

    public void setTotalSupply(long totalSupply) {
        this.totalSupply = totalSupply;
    }

    public int getCmcRank() {
        return cmcRank;
    }

    public void setCmcRank(int cmcRank) {
        this.cmcRank = cmcRank;
    }
    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }
    public QuoteData getQuote() {
        return quote;
    }

    public void setQuote(QuoteData quote) {
        this.quote = quote;
    }

    public int getNumMarketPairs() {
        return numMarketPairs;
    }

    public void setNumMarketPairs(int numMarketPairs) {
        this.numMarketPairs = numMarketPairs;
    }
    public boolean isInfiniteSupply() {
        return infiniteSupply;
    }

    public void setInfiniteSupply(boolean infiniteSupply) {
        this.infiniteSupply = infiniteSupply;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getMaxSupply() {
        return maxSupply;
    }

    public void setMaxSupply(long maxSupply) {
        this.maxSupply = maxSupply;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }
}

