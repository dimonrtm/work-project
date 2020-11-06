package hello.model;

import java.io.Serializable;
import java.util.Objects;

public class SellMarkingProductKey implements Serializable {
    private String sellMarkingProductId;
    private String sellId;
    private String productCode;

    public SellMarkingProductKey(){

    }

    public SellMarkingProductKey(String sellMarkingProductId,String sellId,String productCode){
        this.sellMarkingProductId=sellMarkingProductId;
        this.sellId=sellId;
        this.productCode=productCode;
    }

    public String getSellMarkingProductId() {
        return sellMarkingProductId;
    }

    public void setSellMarkingProductId(String sellMarkingProductId) {
        this.sellMarkingProductId = sellMarkingProductId;
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
        if (!(o instanceof SellMarkingProductKey)) return false;
        SellMarkingProductKey that = (SellMarkingProductKey) o;
        return Objects.equals(getSellMarkingProductId(), that.getSellMarkingProductId()) &&
                Objects.equals(getSellId(), that.getSellId()) &&
                Objects.equals(getProductCode(), that.getProductCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSellMarkingProductId(), getSellId(), getProductCode());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SellMarkingProductKey{");
        sb.append("sellMarkingProductId='").append(sellMarkingProductId).append('\'');
        sb.append(", sellId='").append(sellId).append('\'');
        sb.append(", productCode='").append(productCode).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
