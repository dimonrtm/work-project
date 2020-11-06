package hello.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name="sell_marking_products")
@IdClass(SellMarkingProductKey.class)
@JacksonXmlRootElement(localName = "SellMarkingProduct")
public class SellMarkingProduct implements Serializable {
    private static final long serialVersionUID=44L;

    @Id
    @JacksonXmlProperty
    String sellMarkingProductId;
    @Id
    @JacksonXmlProperty
    String sellId;
    @Id
    @JacksonXmlProperty
    String productCode;
    @JacksonXmlProperty
    String barcodeLabeling;
    @JacksonXmlProperty
    boolean markingCompleted;

    public SellMarkingProduct(){

    }

    public SellMarkingProduct(String sellMarkingProductId,String sellId,String productCode,String barcodeLabeling,boolean markingCompleted){
        this.sellMarkingProductId=sellMarkingProductId;
        this.sellId=sellId;
        this.productCode=productCode;
        this.barcodeLabeling=barcodeLabeling;
        this.markingCompleted=markingCompleted;
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

    public String getBarcodeLabeling() {
        return barcodeLabeling;
    }

    public void setBarcodeLabeling(String barcodeLabeling) {
        this.barcodeLabeling = barcodeLabeling;
    }

    public boolean isMarkingCompleted() {
        return markingCompleted;
    }

    public void setMarkingCompleted(boolean markingCompleted) {
        this.markingCompleted = markingCompleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SellMarkingProduct)) return false;
        SellMarkingProduct that = (SellMarkingProduct) o;
        return isMarkingCompleted() == that.isMarkingCompleted() &&
                Objects.equals(getSellMarkingProductId(), that.getSellMarkingProductId()) &&
                Objects.equals(getSellId(), that.getSellId()) &&
                Objects.equals(getProductCode(), that.getProductCode()) &&
                Objects.equals(getBarcodeLabeling(), that.getBarcodeLabeling());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSellMarkingProductId(), getSellId(), getProductCode(), getBarcodeLabeling(), isMarkingCompleted());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SellMarkingProduct{");
        sb.append("sellMarkingProductId='").append(sellMarkingProductId).append('\'');
        sb.append(", sellId='").append(sellId).append('\'');
        sb.append(", productCode='").append(productCode).append('\'');
        sb.append(", barcodeLabeling='").append(barcodeLabeling).append('\'');
        sb.append(", markingCompleted=").append(markingCompleted);
        sb.append('}');
        return sb.toString();
    }
}
