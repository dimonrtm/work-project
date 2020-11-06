package hello.model;

import java.io.Serializable;
import java.util.Objects;

public class AdmissionProductKey implements Serializable {
    private String admissionId;
    private String productCode;

    public AdmissionProductKey(){

    }

    public AdmissionProductKey(String admissionId,String productCode){
        this.admissionId=admissionId;
        this.productCode=productCode;
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
        if (!(o instanceof AdmissionProductKey)) return false;
        AdmissionProductKey that = (AdmissionProductKey) o;
        return Objects.equals(getAdmissionId(), that.getAdmissionId()) &&
                Objects.equals(getProductCode(), that.getProductCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAdmissionId(), getProductCode());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AdmissionProductKey{");
        sb.append("admissionId='").append(admissionId).append('\'');
        sb.append(", productCode='").append(productCode).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
