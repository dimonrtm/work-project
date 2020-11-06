package hello.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name="barcodes")
@JacksonXmlRootElement(localName="Barcode")
public class Barcode implements Serializable {
    private static final long serialVersionUID=28L;

    @Id
    @JacksonXmlProperty
    private String barcodeValue;
    @JacksonXmlProperty
    private String productCodeBarcode;

    public Barcode(){

    }

    public Barcode(String barcodeValue,String productCodeBarcode){
        this.barcodeValue=barcodeValue;
        this.productCodeBarcode=productCodeBarcode;

    }

    public String getBarcodeValue() {
        return barcodeValue;
    }

    public void setBarcodeValue(String barcodeValue) {
        this.barcodeValue = barcodeValue;
    }

    public String getProductCodeBarcode() {
        return productCodeBarcode;
    }

    public void setProductCodeBarcode(String productCodeBarcode) {
        this.productCodeBarcode = productCodeBarcode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Barcode)) return false;
        Barcode barcode = (Barcode) o;
        return Objects.equals(getBarcodeValue(), barcode.getBarcodeValue()) &&
                Objects.equals(getProductCodeBarcode(), barcode.getProductCodeBarcode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBarcodeValue(), getProductCodeBarcode());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Barcode{");
        sb.append("barcodeValue='").append(barcodeValue).append('\'');
        sb.append(", productCodeBarcode='").append(productCodeBarcode).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
