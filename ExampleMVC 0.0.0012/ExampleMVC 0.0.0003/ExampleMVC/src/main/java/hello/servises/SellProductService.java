package hello.servises;

import hello.model.*;
import hello.repository.AddAdmissionProductRepository;
import hello.repository.AddSellProductRepository;
import hello.repository.AdmissionProductRepository;
import hello.repository.SellProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SellProductService implements ISellProductService {
    @Autowired
    SellProductRepository sellProductRepository;
    @Autowired
    AddSellProductRepository addSellProductRepository;

    @Override
    public SellProducts findAll(){
        List<SellProduct> sellProducts=(List<SellProduct>)sellProductRepository.findAll();
        SellProducts serSellProducts=new SellProducts();
        serSellProducts.setSellProducts(sellProducts);
        return serSellProducts;
    }

    @Override
    public void insertSellProduct(SellProduct sellProduct){
        addSellProductRepository.insertSellProduct(sellProduct);
    }

    @Override
    public SellProduct getSellProductById(SellProductKey id){
        SellProduct sellProduct=sellProductRepository.findById(id).orElse(new SellProduct());
        return sellProduct;
    }

    @Override
    public void updateSellProduct(String sellId,String productCode,int balanceValue,int balanceValueDoc,boolean marked) throws Exception {
        SellProductKey sellProductKey=new SellProductKey(sellId,productCode);
        SellProduct sellProduct=sellProductRepository.findById(sellProductKey).orElseThrow(()->new Exception("Product not Found in Data Base"));
        sellProduct.setBalanceValueSellProducts(balanceValue);
        sellProduct.setBalanceValueDoc(balanceValueDoc);
        sellProduct.setMarking(marked);
        sellProductRepository.save(sellProduct);
    }

    @Override
    public void deleteById(SellProductKey id){ sellProductRepository.deleteById(id);
    }
}
