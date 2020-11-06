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
@Table(name="balance")
@IdClass(BalanceKey.class)
@JacksonXmlRootElement(localName="Balance")
public class Balance implements Serializable {
    private static final long serialVersionUID=32L;

    @Id
    @JacksonXmlProperty
    private String productCode;
    @Id
    @JacksonXmlProperty
    private String warehouseCode;
    @JacksonXmlProperty
    private double balanceValue;

    public Balance(){

    }

    public Balance(String productCode,String warehouseCode,double balanceValue){
        this.productCode=productCode;
        this.warehouseCode=warehouseCode;
        this.balanceValue=balanceValue;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public double getBalanceValue() {
        return balanceValue;
    }

    public void setBalanceValue(double balanceValue) {
        this.balanceValue = balanceValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Balance)) return false;
        Balance balance = (Balance) o;
        return Double.compare(balance.getBalanceValue(), getBalanceValue()) == 0 &&
                Objects.equals(getProductCode(), balance.getProductCode()) &&
                Objects.equals(getWarehouseCode(), balance.getWarehouseCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProductCode(), getWarehouseCode(), getBalanceValue());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Balance{");
        sb.append("productCode='").append(productCode).append('\'');
        sb.append(", warehouseCode='").append(warehouseCode).append('\'');
        sb.append(", balanceValue=").append(balanceValue);
        sb.append('}');
        return sb.toString();
    }
}
