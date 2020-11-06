package hello.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name="barcodes_2d")
@JacksonXmlRootElement(localName="Barcode2D")
public class Barcode2D implements Serializable {
    private static final long seralVersionId=30L;

    @Id
    @JacksonXmlProperty
    @Column(name="barcode_2d_value")
    private String barcode2DValue;
    @JacksonXmlProperty
    private String productCode;

    public Barcode2D(){

    }

    public Barcode2D(String barcode2DValue,String productCode){
        this.barcode2DValue=barcode2DValue;
        this.productCode=productCode;
    }

    public String getBarcode2DValue() {
        return barcode2DValue;
    }

    public void setBarcode2DValue(String barcode2DValue) {
        this.barcode2DValue = barcode2DValue;
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
        if (!(o instanceof Barcode2D)) return false;
        Barcode2D barcode2D = (Barcode2D) o;
        return Objects.equals(getBarcode2DValue(), barcode2D.getBarcode2DValue()) &&
                Objects.equals(getProductCode(), barcode2D.getProductCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBarcode2DValue(), getProductCode());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Barcode2D{");
        sb.append("barcode2DValue='").append(barcode2DValue).append('\'');
        sb.append(", productCode='").append(productCode).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
