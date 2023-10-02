import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CryptoDataResponse {

    @JsonProperty("data")
    private List<CoinMarketData> data;

    public CryptoDataResponse() {
    }

    public List<CoinMarketData> getData() {
        return data;
    }

    public void setData(List<CoinMarketData> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "CryptoDataResponse [data=" + data + "]";
    }

}
