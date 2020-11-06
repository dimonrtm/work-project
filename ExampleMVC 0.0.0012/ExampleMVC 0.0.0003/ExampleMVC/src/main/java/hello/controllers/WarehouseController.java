package hello.controllers;

import hello.model.*;
import hello.servises.IWarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
public class WarehouseController {

    @Autowired
    IWarehouseService warehouseService;

    @GetMapping(value="/warehouses",produces= MediaType.APPLICATION_XML_VALUE)
    public @ResponseBody
    Warehouses showWarehouses() {
        return warehouseService.findAll();
    }

    @RequestMapping(value = "/warehouses/add", method = RequestMethod.POST,produces=MediaType.APPLICATION_XML_VALUE)
    public @ResponseBody
    Result addWarehouse(@RequestParam("warehouse_code") String warehouseCode,
                      @RequestParam("warehouse_name") String warehouseName) throws IOException {
        Warehouse warehouse =new Warehouse(warehouseCode,warehouseName);
        warehouseService.insertWarehouse(warehouse);
        return new Result("Add Warehouse");
    }

    @GetMapping(value="/warehouses/{warehouse_code}",produces=MediaType.APPLICATION_XML_VALUE)
    public @ResponseBody Warehouse showWarehouseById(@PathVariable("warehouse_code") String warehouseCode) throws IOException {
        return warehouseService.getWarehouseById(warehouseCode);
    }

    @RequestMapping(value="/warehouses/{warehouse_code}",method=RequestMethod.PUT,produces=MediaType.APPLICATION_XML_VALUE)
    public @ResponseBody Result updateUser(@PathVariable("warehouse_code") String warehouseCode,
                                           @RequestParam("warehouse_name") String warehouseName) throws Exception {
        warehouseService.updateWarehouse(warehouseCode,warehouseName);
        return new Result("Warehouse update");
    }

    @RequestMapping(value = "/warehouses/{warehouse_code}", method = RequestMethod.DELETE,produces=MediaType.APPLICATION_XML_VALUE)
    public @ResponseBody Result deleteWarehouse(@PathVariable("warehouse_code") String warehouseCode) throws IOException {
        warehouseService.deleteById(warehouseCode);
        return new Result("Warehouse Delete");
    }
}
