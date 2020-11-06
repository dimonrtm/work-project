package hello.servises;

import hello.model.Barcode;
import hello.model.Barcode2D;
import hello.model.Barcodes;
import hello.model.Barcodes2D;

public interface IBarcode2DService {
    Barcodes2D findAll();
    void insertBarcode2D(Barcode2D barcode2D);
    void deleteById(String id);
    Barcode2D getBarcode2DById(String id);
    void updateBarcode2D(String barcode2DValue,String productCodeBarcode2D) throws Exception;
}
