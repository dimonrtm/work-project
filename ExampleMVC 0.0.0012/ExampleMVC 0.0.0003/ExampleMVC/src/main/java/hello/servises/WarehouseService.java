package hello.servises;

import hello.model.Product;
import hello.model.Products;
import hello.model.Warehouse;
import hello.model.Warehouses;
import hello.repository.AddWarehouseRepository;
import hello.repository.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WarehouseService implements IWarehouseService {
    @Autowired
    WarehouseRepository warehouseRepository;
    @Autowired
    AddWarehouseRepository addWarehouseRepository;

    @Override
    public Warehouses findAll(){
        List<Warehouse> warehouses=(List<Warehouse>)warehouseRepository.findAll();
        Warehouses serWarehouses=new Warehouses();
        serWarehouses.setWarehouses(warehouses);
        return serWarehouses;
    }

    @Override
    public void insertWarehouse(Warehouse warehouse){
        addWarehouseRepository.insertWarehouse(warehouse);
    }

    @Override
    public void deleteById(String id){
        warehouseRepository.deleteById(id);
    }

    @Override
    public Warehouse getWarehouseById(String id){
        Warehouse warehouse=warehouseRepository.findById(id).orElse(new Warehouse());
        return warehouse;
    }

    @Override
    public void updateWarehouse(String warehouseCode,String warehouseName) throws Exception {
        Warehouse warehouse=warehouseRepository.findById(warehouseCode).orElseThrow(()->new Exception("Product not Found in Data Base"));
        warehouse.setWarehouseName(warehouseName);
        warehouseRepository.save(warehouse);
    }
}
