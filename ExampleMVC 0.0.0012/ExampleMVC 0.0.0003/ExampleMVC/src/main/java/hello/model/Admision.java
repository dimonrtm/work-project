package hello.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.util.Objects;

@Entity
@Table(name="admissions")
@IdClass(AdmissionKey.class)
@JacksonXmlRootElement(localName = "Admission")
public class Admision implements Serializable {
    private static final long serialVersionUID=38L;

    @Id
    @JacksonXmlProperty
    private String admissionId;
    @Id
    @JacksonXmlProperty
    private String warehouseId;
    @JacksonXmlProperty
    private Date admissionDate;
    @JacksonXmlProperty
    private Time admissionTime;

    public Admision(){

    }

    public Admision(String admissionId,String warehouseId,Date admissionDate,Time admissionTime){
        this.admissionId=admissionId;
        this.warehouseId=warehouseId;
        this.admissionDate=admissionDate;
        this.admissionTime=admissionTime;
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

    public Date getAdmissionDate() {
        return admissionDate;
    }

    public void setAdmissionDate(Date admissionDate) {
        this.admissionDate = admissionDate;
    }

    public Time getAdmissionTime() {
        return admissionTime;
    }

    public void setAdmissionTime(Time admissionTime) {
        this.admissionTime = admissionTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Admision)) return false;
        Admision admision = (Admision) o;
        return Objects.equals(getAdmissionId(), admision.getAdmissionId()) &&
                Objects.equals(getWarehouseId(), admision.getWarehouseId()) &&
                Objects.equals(getAdmissionDate(), admision.getAdmissionDate()) &&
                Objects.equals(getAdmissionTime(), admision.getAdmissionTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAdmissionId(), getWarehouseId(), getAdmissionDate(), getAdmissionTime());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Admision{");
        sb.append("admissionId='").append(admissionId).append('\'');
        sb.append(", warehouseCode='").append(warehouseId).append('\'');
        sb.append(", admissionDate=").append(admissionDate);
        sb.append(", admissionTime=").append(admissionTime);
        sb.append('}');
        return sb.toString();
    }
}
