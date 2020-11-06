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
@Table(name="sell_products")
@IdClass(SellProductKey.class)
@JacksonXmlRootElement(localName="SellProduct")
public class SellProduct implements Serializable {
    private static final long serialVersionUID=42L;

    @Id
    @JacksonXmlProperty
    private String sellId;
    @Id
    @JacksonXmlProperty
    private String productCode;
    @JacksonXmlProperty
    private int balanceValueSellProducts;
    @JacksonXmlProperty
    private int balanceValueDoc;
    @JacksonXmlProperty
    private boolean marking;

    public SellProduct(){

    }

    public SellProduct(String sellId,String productCode,int balanceValueSellProducts,int balanceValueDoc,boolean marking){
        this.sellId=sellId;
        this.productCode=productCode;
        this.balanceValueSellProducts=balanceValueSellProducts;
        this.balanceValueDoc=balanceValueDoc;
        this.marking=marking;
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

    public int getBalanceValueSellProducts() {
        return balanceValueSellProducts;
    }

    public void setBalanceValueSellProducts(int balanceValueSellProducts) {
        this.balanceValueSellProducts = balanceValueSellProducts;
    }

    public int getBalanceValueDoc() {
        return balanceValueDoc;
    }

    public void setBalanceValueDoc(int balanceValueDoc) {
        this.balanceValueDoc = balanceValueDoc;
    }

    public boolean isMarking() {
        return marking;
    }

    public void setMarking(boolean marking) {
        this.marking = marking;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SellProduct)) return false;
        SellProduct that = (SellProduct) o;
        return getBalanceValueSellProducts() == that.getBalanceValueSellProducts() &&
                getBalanceValueDoc() == that.getBalanceValueDoc() &&
                isMarking() == that.isMarking() &&
                Objects.equals(getSellId(), that.getSellId()) &&
                Objects.equals(getProductCode(), that.getProductCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSellId(), getProductCode(), getBalanceValueSellProducts(), getBalanceValueDoc(), isMarking());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SellProduct{");
        sb.append("sellId='").append(sellId).append('\'');
        sb.append(", productCode='").append(productCode).append('\'');
        sb.append(", balanceValueSellProducts=").append(balanceValueSellProducts);
        sb.append(", balanceValueDoc=").append(balanceValueDoc);
        sb.append(", marking=").append(marking);
        sb.append('}');
        return sb.toString();
    }
}
