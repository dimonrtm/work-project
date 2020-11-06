package hello.servises;

import hello.model.Product;
import hello.model.Products;
import hello.model.Warehouse;
import hello.model.Warehouses;

public interface IWarehouseService {
    Warehouses findAll();
    void insertWarehouse(Warehouse warehouse);
    void deleteById(String id);
    Warehouse getWarehouseById(String id);
    void updateWarehouse(String warehouseCode,String warehouseName) throws Exception;
}
