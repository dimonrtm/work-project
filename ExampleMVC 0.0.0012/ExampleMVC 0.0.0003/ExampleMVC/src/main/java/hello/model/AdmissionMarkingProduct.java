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
@Table(name="admission_marking_products")
@IdClass(AdmissionMarkingProductKey.class)
@JacksonXmlRootElement(localName = "AdmissionMarkingProduct")
public class AdmissionMarkingProduct implements Serializable {
    private static final long serialVersionUID=36L;

    @Id
    @JacksonXmlProperty
    String admissionMarkingProductId;
    @Id
    @JacksonXmlProperty
    String admissionId;
    @Id
    @JacksonXmlProperty
    String productCode;
    @JacksonXmlProperty
    String barcodeLabeling;
    @JacksonXmlProperty
    boolean markingCompleted;

    public AdmissionMarkingProduct(){

    }

    public AdmissionMarkingProduct(String admissionMarkingProductId,String admissionId,String productCode,String barcodeLabeling,boolean markingCompleted){
        this.admissionMarkingProductId=admissionMarkingProductId;
        this.admissionId=admissionId;
        this.productCode=productCode;
        this.barcodeLabeling=barcodeLabeling;
        this.markingCompleted=markingCompleted;
    }

    public String getAdmissionMarkingProductId() {
        return admissionMarkingProductId;
    }

    public void setAdmissionMarkingProductId(String admissionMarkingProductId) {
        this.admissionMarkingProductId = admissionMarkingProductId;
    }

    public String getAdmissionId() {
        return admissionId;
    }

    public void setAdmissionId(String admissionId) {
        this.admissionId = admissionId;
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
        if (!(o instanceof AdmissionMarkingProduct)) return false;
        AdmissionMarkingProduct that = (AdmissionMarkingProduct) o;
        return isMarkingCompleted() == that.isMarkingCompleted() &&
                Objects.equals(getAdmissionMarkingProductId(), that.getAdmissionMarkingProductId()) &&
                Objects.equals(getAdmissionId(), that.getAdmissionId()) &&
                Objects.equals(getProductCode(), that.getProductCode()) &&
                Objects.equals(getBarcodeLabeling(), that.getBarcodeLabeling());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAdmissionMarkingProductId(), getAdmissionId(), getProductCode(), getBarcodeLabeling(), isMarkingCompleted());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AdmissionMarkingProduct{");
        sb.append("admissionMarkingProductId='").append(admissionMarkingProductId).append('\'');
        sb.append(", admissionId='").append(admissionId).append('\'');
        sb.append(", productCode='").append(productCode).append('\'');
        sb.append(", barcodeLabeling='").append(barcodeLabeling).append('\'');
        sb.append(", markingCompleted=").append(markingCompleted);
        sb.append('}');
        return sb.toString();
    }
}
