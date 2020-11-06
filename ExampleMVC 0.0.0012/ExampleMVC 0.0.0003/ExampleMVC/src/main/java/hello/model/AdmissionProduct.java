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
@Table(name="admission_products")
@IdClass(AdmissionProductKey.class)
@JacksonXmlRootElement(localName ="AdmissionProduct")
public class AdmissionProduct implements Serializable {
    private static final long serialVersionUID=34L;

    @Id
    @JacksonXmlProperty
    private String admissionId;
    @Id
    @JacksonXmlProperty
    private String productCode;
    @JacksonXmlProperty
    private int balanceValueAdmissionProducts;
    @JacksonXmlProperty
    private int balanceValueDoc;
    @JacksonXmlProperty
    private boolean marking;

    public AdmissionProduct(){

    }

    public AdmissionProduct(String admissionId,String productCode,int balanceValueAdmissionProducts,int balanceValueDoc,boolean marking){
        this.admissionId=admissionId;
        this.productCode=productCode;
        this.balanceValueAdmissionProducts=balanceValueAdmissionProducts;
        this.balanceValueDoc=balanceValueDoc;
        this.marking=marking;
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

    public int getBalanceValueAdmissionProducts() {
        return balanceValueAdmissionProducts;
    }

    public void setBalanceValueAdmissionProducts(int balanceValueAdmissionProducts) {
        this.balanceValueAdmissionProducts = balanceValueAdmissionProducts;
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
        if (!(o instanceof AdmissionProduct)) return false;
        AdmissionProduct that = (AdmissionProduct) o;
        return getBalanceValueAdmissionProducts() == that.getBalanceValueAdmissionProducts() &&
                getBalanceValueDoc() == that.getBalanceValueDoc() &&
                isMarking() == that.isMarking() &&
                Objects.equals(getAdmissionId(), that.getAdmissionId()) &&
                Objects.equals(getProductCode(), that.getProductCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAdmissionId(), getProductCode(), getBalanceValueAdmissionProducts(), getBalanceValueDoc(), isMarking());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AdmissionProduct{");
        sb.append("admissionId='").append(admissionId).append('\'');
        sb.append(", productCode='").append(productCode).append('\'');
        sb.append(", balanceValueAdmissionProducts=").append(balanceValueAdmissionProducts);
        sb.append(", balanceValueDoc=").append(balanceValueDoc);
        sb.append(", marking=").append(marking);
        sb.append('}');
        return sb.toString();
    }
}
