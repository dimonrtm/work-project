package hello.model;

import java.io.Serializable;
import java.util.Objects;

public class BalanceKey implements Serializable {
   private String productCode;
   private String warehouseCode;

   public BalanceKey(){

   }

   public BalanceKey(String productCode,String warehouseCode){
       this.productCode=productCode;
       this.warehouseCode=warehouseCode;
   }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BalanceKey)) return false;
        BalanceKey that = (BalanceKey) o;
        return Objects.equals(getProductCode(), that.getProductCode()) &&
                Objects.equals(getWarehouseCode(), that.getWarehouseCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProductCode(), getWarehouseCode());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BalanceKey{");
        sb.append("productCode='").append(productCode).append('\'');
        sb.append(", warehouseCode='").append(warehouseCode).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
