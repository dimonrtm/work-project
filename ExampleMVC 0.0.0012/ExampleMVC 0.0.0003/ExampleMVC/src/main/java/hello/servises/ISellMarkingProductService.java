package hello.servises;

import hello.model.*;

public interface ISellMarkingProductService {
    SellMarkingProducts findAll();
    void insertSellMarkingProduct(SellMarkingProduct sellMarkingProduct);
    SellMarkingProduct getSellMarkingProductById(SellMarkingProductKey id);
    void updateSellMarkingProduct(String sellMarkingProductId,String sellId,String productCode,String barcodeLabeling,boolean marking_completed) throws Exception;
    void deleteById(SellMarkingProductKey id);
}
