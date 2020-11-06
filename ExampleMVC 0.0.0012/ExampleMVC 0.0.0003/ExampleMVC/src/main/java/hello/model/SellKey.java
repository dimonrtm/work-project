package hello.model;

import java.io.Serializable;
import java.util.Objects;

public class SellKey implements Serializable {
    private String sellId;
    private String warehouseId;

    public SellKey(){

    }

    public SellKey(String sellId,String warehouseId){
        this.sellId=sellId;
        this.warehouseId=warehouseId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SellKey)) return false;
        SellKey that = (SellKey) o;
        return Objects.equals(getSellId(), that.getSellId()) &&
                Objects.equals(getWarehouseId(), that.getWarehouseId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSellId(), getWarehouseId());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SellKey{");
        sb.append("sellId='").append(sellId).append('\'');
        sb.append(", warehouseCode='").append(warehouseId).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
