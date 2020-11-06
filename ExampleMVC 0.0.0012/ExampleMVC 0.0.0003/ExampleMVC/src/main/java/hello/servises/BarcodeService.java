package hello.servises;

import hello.model.Barcode;
import hello.model.Barcodes;
import hello.model.Product;
import hello.model.Products;
import hello.repository.AddBarcodeRepository;
import hello.repository.BarcodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BarcodeService implements IBarcodeService {
    @Autowired
    BarcodeRepository barcodeRepository;
    @Autowired
    AddBarcodeRepository addBarcodeRepository;

    @Override
    public Barcodes findAll(){
        List<Barcode> barcodes=(List<Barcode>)barcodeRepository.findAll();
        Barcodes serBarcodes=new Barcodes();
        serBarcodes.setBarcodes(barcodes);
        return serBarcodes;
    }

    @Override
    public void insertBarcode(Barcode barcode){
        addBarcodeRepository.insertBarcode(barcode);
    }

    @Override
    public void deleteById(String id){
        barcodeRepository.deleteById(id);
    }

    @Override
    public Barcode getBarcodeById(String id){
        Barcode barcode=barcodeRepository.findById(id).orElse(new Barcode());
        return barcode;
    }

    @Override
    public void updateBarcode(String barcodeValue,String productCodeBarcode) throws Exception {
        Barcode barcode=barcodeRepository.findById(barcodeValue).orElseThrow(()->new Exception("Product not Found in Data Base"));
        barcode.setProductCodeBarcode(productCodeBarcode);
        barcodeRepository.save(barcode);
    }
}
