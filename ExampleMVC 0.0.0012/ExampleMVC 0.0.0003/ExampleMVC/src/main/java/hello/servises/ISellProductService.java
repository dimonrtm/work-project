package hello.servises;

import hello.model.*;

public interface ISellProductService {
    SellProducts findAll();
    void insertSellProduct(SellProduct sellProduct);
    SellProduct getSellProductById(SellProductKey id);
    void updateSellProduct(String productCode,String warehouseCode,int balanceValue,int balanceValueDoc,boolean marked) throws Exception;
    void deleteById(SellProductKey id);
}
