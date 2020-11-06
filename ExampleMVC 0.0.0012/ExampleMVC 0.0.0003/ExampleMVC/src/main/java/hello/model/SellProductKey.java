package hello.model;

import java.io.Serializable;
import java.util.Objects;

public class SellProductKey implements Serializable {
    private String sellId;
    private String productCode;

    public SellProductKey(){

    }

    public SellProductKey(String sellId,String productCode){
        this.sellId=sellId;
        this.productCode=productCode;
    }

    public String getSellId() {
        return sellId;
    }

    public void setSellId(String sellId) {
        this.sellId = sellId;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SellProductKey)) return false;
        SellProductKey that = (SellProductKey) o;
        return Objects.equals(getSellId(), that.getSellId()) &&
                Objects.equals(getProductCode(), that.getProductCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSellId(), getProductCode());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SellProductKey{");
        sb.append("sellId='").append(sellId).append('\'');
        sb.append(", productCode='").append(productCode).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
