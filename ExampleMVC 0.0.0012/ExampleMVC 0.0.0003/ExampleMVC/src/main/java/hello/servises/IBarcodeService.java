package hello.servises;

import hello.model.Barcode;
import hello.model.Barcodes;
import hello.model.Product;
import hello.model.Products;

public interface IBarcodeService {
    Barcodes findAll();
    void insertBarcode(Barcode barcode);
    void deleteById(String id);
    Barcode getBarcodeById(String id);
    void updateBarcode(String barcodeValue,String productCodeBarcode) throws Exception;
}
