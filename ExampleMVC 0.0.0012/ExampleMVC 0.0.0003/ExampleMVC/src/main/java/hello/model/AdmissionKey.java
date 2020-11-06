package hello.model;

import java.io.Serializable;
import java.util.Objects;

public class AdmissionKey implements Serializable {
    private String admissionId;
    private String warehouseId;

    public AdmissionKey(){

    }

    public AdmissionKey(String admissionId,String warehouseId){
        this.admissionId=admissionId;
        this.warehouseId=warehouseId;
    }

    public String getAdmissionId() {
        return admissionId;
    }

    public void setAdmissionId(String admissionId) {
        this.admissionId = admissionId;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AdmissionKey)) return false;
        AdmissionKey that = (AdmissionKey) o;
        return Objects.equals(getAdmissionId(), that.getAdmissionId()) &&
                Objects.equals(getWarehouseId(), that.getWarehouseId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAdmissionId(), getWarehouseId());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AdmissionKey{");
        sb.append("admissionId='").append(admissionId).append('\'');
        sb.append(", warehouseCode='").append(warehouseId).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
