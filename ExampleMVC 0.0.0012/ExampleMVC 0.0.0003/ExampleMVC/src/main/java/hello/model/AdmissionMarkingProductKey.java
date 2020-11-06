package hello.model;

import java.io.Serializable;
import java.util.Objects;

public class AdmissionMarkingProductKey implements Serializable {
    private String admissionMarkingProductId;
    private String admissionId;
    private String productCode;

    public AdmissionMarkingProductKey(){

    }

    public AdmissionMarkingProductKey(String admissionMarkingProductId,String admissionId,String productCode){
        this.admissionMarkingProductId=admissionMarkingProductId;
        this.admissionId=admissionId;
        this.productCode=productCode;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AdmissionMarkingProductKey)) return false;
        AdmissionMarkingProductKey that = (AdmissionMarkingProductKey) o;
        return Objects.equals(getAdmissionMarkingProductId(), that.getAdmissionMarkingProductId()) &&
                Objects.equals(getAdmissionId(), that.getAdmissionId()) &&
                Objects.equals(getProductCode(), that.getProductCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAdmissionMarkingProductId(), getAdmissionId(), getProductCode());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AdmissionMarkingProductKey{");
        sb.append("admissionMarkingProductId='").append(admissionMarkingProductId).append('\'');
        sb.append(", admissionId='").append(admissionId).append('\'');
        sb.append(", productCode='").append(productCode).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
