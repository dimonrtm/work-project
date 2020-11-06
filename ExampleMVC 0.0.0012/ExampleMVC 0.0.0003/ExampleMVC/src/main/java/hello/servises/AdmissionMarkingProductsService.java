package hello.servises;

import hello.model.*;
import hello.repository.AddAdmissionMarkingProductRepository;
import hello.repository.AdmissionMarkingProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdmissionMarkingProductsService implements IAdmissionMarkingProductServise {
    @Autowired
    AdmissionMarkingProductRepository admissionMarkingProductRepository;
    @Autowired
    AddAdmissionMarkingProductRepository addAdmissionMarkingProductRepository;

    @Override
    public AdmissionMarkingProducts findAll(){
        List<AdmissionMarkingProduct> admissionMarkingProducts=(List<AdmissionMarkingProduct>)admissionMarkingProductRepository.findAll();
        AdmissionMarkingProducts serAdmissionMarkingProducts=new AdmissionMarkingProducts();
        serAdmissionMarkingProducts.setAdmissionMarkingProducts(admissionMarkingProducts);
        return serAdmissionMarkingProducts;
    }

    @Override
    public void insertAdmissionMarkingProduct(AdmissionMarkingProduct admissionMarkingProduct){
        addAdmissionMarkingProductRepository.insertAdmissionMarkingProduct(admissionMarkingProduct);
    }

    @Override
    public AdmissionMarkingProduct getAdmissionMarkingProductById(AdmissionMarkingProductKey id){
        AdmissionMarkingProduct admissionMarkingProduct=admissionMarkingProductRepository.findById(id).orElse(new AdmissionMarkingProduct());
        return admissionMarkingProduct;
    }
    @Override
    public void updateAdmissionMarkingProduct(String admissionMarkingProductId,String admissionId,String productCode,String barcodeLabeling,boolean markingCompleted) throws Exception {
        AdmissionMarkingProductKey admissionMarkingProductKey=new AdmissionMarkingProductKey(admissionMarkingProductId,admissionId,productCode);
        AdmissionMarkingProduct admissionMarkingProduct=admissionMarkingProductRepository.findById(admissionMarkingProductKey).orElseThrow(()->new Exception("Product not Found in Data Base"));
        admissionMarkingProduct.setBarcodeLabeling(barcodeLabeling);
        admissionMarkingProduct.setMarkingCompleted(markingCompleted);
        admissionMarkingProductRepository.save(admissionMarkingProduct);
    }

    @Override
    public void deleteById(AdmissionMarkingProductKey id){ admissionMarkingProductRepository.deleteById(id);
    }
}
