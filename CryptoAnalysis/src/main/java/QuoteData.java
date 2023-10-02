import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class QuoteData {
    @JsonProperty("USD")
    private QuoteUSD usd;

    // Add getters and setters

    public QuoteData() {
    }

    public QuoteUSD getUsd() {
        return usd;
    }

    public void setUsd(QuoteUSD usd) {
        this.usd = usd;
    }

    @Override
    public String toString() {
        return "QuoteData [usd=" + usd + "]";
    }

}

@JsonIgnoreProperties(ignoreUnknown = true)
class QuoteUSD {
    @JsonProperty("fully_diluted_market_cap")
    private double fullyDilutedMarketCap;

    @JsonProperty("last_updated")
    private String lastUpdated;

    @JsonProperty("market_cap_dominance")
    private double marketCapDominance;
    @JsonProperty("tvl")
    private String tvl;

    @JsonProperty("percent_change_30d")
    private double percentChange30d;

    @JsonProperty("percent_change_1h")
    private double percentChange1h;

    @JsonProperty("percent_change_24h")
    private double percentChange24h;

    @JsonProperty("market_cap")
    private double marketCap;

    @JsonProperty("volume_change_24h")
    private double volumeChange24h;

    @JsonProperty("price")
    private double price;

    @JsonProperty("percent_change_60d")
    private double percentChange60d;

    @JsonProperty("volume_24h")
    private double volume24h;

    @JsonProperty("percent_change_90d")
    private double percentChange90d;

    @JsonProperty("percent_change_7d")
    private double percentChange7d;

    // Add getters and setters

    public QuoteUSD() {
    }

    @Override
    public String toString() {
        return "QuoteUSD [fullyDilutedMarketCap=" + fullyDilutedMarketCap
                + ", lastUpdated=" + lastUpdated + ", marketCapDominance="
                + marketCapDominance + ", tvl=" + tvl + ", percentChange30d="
                + percentChange30d + ", percentChange1h=" + percentChange1h
                + ", percentChange24h=" + percentChange24h + ", marketCap="
                + marketCap + ", volumeChange24h=" + volumeChange24h
                + ", price=" + price + ", percentChange60d=" + percentChange60d
                + ", volume24h=" + volume24h + ", percentChange90d="
                + percentChange90d + ", percentChange7d=" + percentChange7d
                + "]";
    }

    public double getFullyDilutedMarketCap() {
        return fullyDilutedMarketCap;
    }

    public void setFullyDilutedMarketCap(double fullyDilutedMarketCap) {
        this.fullyDilutedMarketCap = fullyDilutedMarketCap;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public double getMarketCapDominance() {
        return marketCapDominance;
    }

    public void setMarketCapDominance(double marketCapDominance) {
        this.marketCapDominance = marketCapDominance;
    }

    public String getTvl() {
        return tvl;
    }

    public void setTvl(String tvl) {
        this.tvl = tvl;
    }

    public double getPercentChange30d() {
        return percentChange30d;
    }

    public void setPercentChange30d(double percentChange30d) {
        this.percentChange30d = percentChange30d;
    }

    public double getPercentChange1h() {
        return percentChange1h;
    }

    public void setPercentChange1h(double percentChange1h) {
        this.percentChange1h = percentChange1h;
    }

    public double getPercentChange24h() {
        return percentChange24h;
    }

    public void setPercentChange24h(double percentChange24h) {
        this.percentChange24h = percentChange24h;
    }

    public double getMarketCap() {
        return marketCap;
    }

    public void setMarketCap(double marketCap) {
        this.marketCap = marketCap;
    }

    public double getVolumeChange24h() {
        return volumeChange24h;
    }

    public void setVolumeChange24h(double volumeChange24h) {
        this.volumeChange24h = volumeChange24h;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPercentChange60d() {
        return percentChange60d;
    }

    public void setPercentChange60d(double percentChange60d) {
        this.percentChange60d = percentChange60d;
    }

    public double getVolume24h() {
        return volume24h;
    }

    public void setVolume24h(double volume24h) {
        this.volume24h = volume24h;
    }

    public double getPercentChange90d() {
        return percentChange90d;
    }

    public void setPercentChange90d(double percentChange90d) {
        this.percentChange90d = percentChange90d;
    }

    public double getPercentChange7d() {
        return percentChange7d;
    }

    public void setPercentChange7d(double percentChange7d) {
        this.percentChange7d = percentChange7d;
    }

}
