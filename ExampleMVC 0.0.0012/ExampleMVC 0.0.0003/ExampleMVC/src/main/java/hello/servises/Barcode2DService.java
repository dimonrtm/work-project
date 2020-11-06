package hello.servises;

import hello.model.Barcode;
import hello.model.Barcode2D;
import hello.model.Barcodes;
import hello.model.Barcodes2D;
import hello.repository.AddBarcode2DRepositiory;
import hello.repository.Barcode2DRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Barcode2DService implements IBarcode2DService {
    @Autowired
    Barcode2DRepository barcode2DRepository;
    @Autowired
    AddBarcode2DRepositiory addBarcode2DRepository;

    @Override
    public Barcodes2D findAll(){
        List<Barcode2D> barcodes2D=(List<Barcode2D>)barcode2DRepository.findAll();
        Barcodes2D serBarcodes2D=new Barcodes2D();
        serBarcodes2D.setBarcodes2D(barcodes2D);
        return serBarcodes2D;
    }

    @Override
    public void insertBarcode2D(Barcode2D barcode2D){
        addBarcode2DRepository.insertBarcode2D(barcode2D);
    }

    @Override
    public void deleteById(String id){
        barcode2DRepository.deleteById(id);
    }

    @Override
    public Barcode2D getBarcode2DById(String id){
        Barcode2D barcode2D=barcode2DRepository.findById(id).orElse(new Barcode2D());
        return barcode2D;
    }

    @Override
    public void updateBarcode2D(String barcode2DValue,String productCodeBarcode2D) throws Exception {
        Barcode2D barcode2D=barcode2DRepository.findById(barcode2DValue).orElseThrow(()->new Exception("Product not Found in Data Base"));
        barcode2D.setProductCode(productCodeBarcode2D);
        barcode2DRepository.save(barcode2D);
    }
}
