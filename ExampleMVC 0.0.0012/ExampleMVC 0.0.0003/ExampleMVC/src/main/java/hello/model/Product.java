package hello.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name="products")
@JacksonXmlRootElement(localName="Product")
public class Product implements Serializable {
    private static final long serialVersionUID=24L;
    @Id
    @JacksonXmlProperty
    private String productCode;
    @JacksonXmlProperty
    private String vendorCode;
    @JacksonXmlProperty
    private String productName;

    public Product(){

    }

    public Product(String productCode,String vendorCode,String productName){
        this.productCode=productCode;
        this.vendorCode=vendorCode;
        this.productName=productName;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getVendorCode() {
        return vendorCode;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return Objects.equals(getProductCode(), product.getProductCode()) &&
                Objects.equals(getVendorCode(), product.getVendorCode()) &&
                Objects.equals(getProductName(), product.getProductName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProductCode(), getVendorCode(), getProductName());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Product{");
        sb.append("productCode='").append(productCode).append('\'');
        sb.append(", vendorCode='").append(vendorCode).append('\'');
        sb.append(", productName='").append(productName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
