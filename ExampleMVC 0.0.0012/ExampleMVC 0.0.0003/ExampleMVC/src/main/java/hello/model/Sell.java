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
@Table(name="sellings")
@IdClass(SellKey.class)
@JacksonXmlRootElement(localName = "Sell")
public class Sell implements Serializable {
    private static final long serialVersionUID=40L;

    @Id
    @JacksonXmlProperty
    private String sellId;
    @Id
    @JacksonXmlProperty
    private String warehouseId;
    @JacksonXmlProperty
    private Date sellDate;
    @JacksonXmlProperty
    private Time sellTime;

    public Sell(){

    }

    public Sell(String sellId,String warehouseId,Date sellDate,Time sellTime){
        this.sellId=sellId;
        this.warehouseId=warehouseId;
        this.sellDate=sellDate;
        this.sellTime=sellTime;
    }

    public String getSellId() {
        return sellId;
    }

    public void setSellId(String sellId) {
        this.sellId = sellId;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public Date getSellDate() {
        return sellDate;
    }

    public void setSellDate(Date sellDate) {
        this.sellDate = sellDate;
    }

    public Time getSellTime() {
        return sellTime;
    }

    public void setSellTime(Time sellTime) {
        this.sellTime = sellTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Sell)) return false;
        Sell sell = (Sell) o;
        return Objects.equals(getSellId(), sell.getSellId()) &&
                Objects.equals(getWarehouseId(), sell.getWarehouseId()) &&
                Objects.equals(getSellDate(), sell.getSellDate()) &&
                Objects.equals(getSellTime(), sell.getSellTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSellId(), getWarehouseId(), getSellDate(), getSellTime());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Sell{");
        sb.append("sellId='").append(sellId).append('\'');
        sb.append(", warehouseCode='").append(warehouseId).append('\'');
        sb.append(", sellDate=").append(sellDate);
        sb.append(", sellTime=").append(sellTime);
        sb.append('}');
        return sb.toString();
    }
}
