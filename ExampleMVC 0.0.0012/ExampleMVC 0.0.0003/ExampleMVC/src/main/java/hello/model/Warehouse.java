package hello.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name="warehouses")
@JacksonXmlRootElement(localName="Warehouse")
public class Warehouse implements Serializable {
    private static final long serialVersionUID=26L;

    @Id
    @JacksonXmlProperty
    private String warehouseCode;
    @JacksonXmlProperty
    private String warehouseName;

    public Warehouse(){

    }

    public Warehouse(String warehouseCode,String warehouseName){
        this.warehouseCode=warehouseCode;
        this.warehouseName=warehouseName;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Warehouse)) return false;
        Warehouse warehouse = (Warehouse) o;
        return Objects.equals(getWarehouseCode(), warehouse.getWarehouseCode()) &&
                Objects.equals(getWarehouseName(), warehouse.getWarehouseName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getWarehouseCode(), getWarehouseName());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Warehouse{");
        sb.append("warehouseCode='").append(warehouseCode).append('\'');
        sb.append(", warehouseName='").append(warehouseName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
