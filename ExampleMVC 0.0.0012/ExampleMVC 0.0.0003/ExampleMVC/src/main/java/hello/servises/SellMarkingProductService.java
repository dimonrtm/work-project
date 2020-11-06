package hello.servises;

import hello.model.*;
import hello.repository.AddAdmissionMarkingProductRepository;
import hello.repository.AddSellMarkingProductRepository;
import hello.repository.AdmissionMarkingProductRepository;
import hello.repository.SellMarkingProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SellMarkingProductService implements ISellMarkingProductService{
    @Autowired
    SellMarkingProductRepository sellMarkingProductRepository;
    @Autowired
    AddSellMarkingProductRepository addSellMarkingProductRepository;

    @Override
    public SellMarkingProducts findAll(){
        List<SellMarkingProduct> sellMarkingProducts=(List<SellMarkingProduct>)sellMarkingProductRepository.findAll();
        SellMarkingProducts serSellMarkingProducts=new SellMarkingProducts();
        serSellMarkingProducts.setSellMarkingProducts(sellMarkingProducts);
        return serSellMarkingProducts;
    }

    @Override
    public void insertSellMarkingProduct(SellMarkingProduct sellMarkingProduct){
        addSellMarkingProductRepository.insertSellMarkingProduct(sellMarkingProduct);
    }

    @Override
    public SellMarkingProduct getSellMarkingProductById(SellMarkingProductKey id){
        SellMarkingProduct sellMarkingProduct=sellMarkingProductRepository.findById(id).orElse(new SellMarkingProduct());
        return sellMarkingProduct;
    }
    @Override
    public void updateSellMarkingProduct(String sellMarkingProductId,String sellId,String productCode,String barcodeLabeling,boolean markingCompleted) throws Exception {
        SellMarkingProductKey sellMarkingProductKey=new SellMarkingProductKey(sellMarkingProductId,sellId,productCode);
        SellMarkingProduct sellMarkingProduct=sellMarkingProductRepository.findById(sellMarkingProductKey).orElseThrow(()->new Exception("Product not Found in Data Base"));
        sellMarkingProduct.setBarcodeLabeling(barcodeLabeling);
        sellMarkingProduct.setMarkingCompleted(markingCompleted);
        sellMarkingProductRepository.save(sellMarkingProduct);
    }

    @Override
    public void deleteById(SellMarkingProductKey id){ sellMarkingProductRepository.deleteById(id);
    }
}
