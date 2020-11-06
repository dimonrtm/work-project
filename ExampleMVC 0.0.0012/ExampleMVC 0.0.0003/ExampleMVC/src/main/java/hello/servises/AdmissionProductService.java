package hello.servises;

import hello.model.*;
import hello.repository.AddAdmissionProductRepository;
import hello.repository.AdmissionProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdmissionProductService implements IAdmissionProductService {

    @Autowired
    AdmissionProductRepository admissionProductRepository;
    @Autowired
    AddAdmissionProductRepository addAdmissionProductRepository;

    @Override
    public AdmissionProducts findAll(){
        List<AdmissionProduct> admissionProducts=(List<AdmissionProduct>)admissionProductRepository.findAll();
        AdmissionProducts serAdmissionProducts=new AdmissionProducts();
        serAdmissionProducts.setAdmissionProducts(admissionProducts);
        return serAdmissionProducts;
    }

    @Override
    public void insertAdmissionProduct(AdmissionProduct admissionProduct){
        addAdmissionProductRepository.insertAdmissionProduct(admissionProduct);
    }

    @Override
    public AdmissionProduct getAdmissionProductById(AdmissionProductKey id){
        AdmissionProduct admissionProduct=admissionProductRepository.findById(id).orElse(new AdmissionProduct());
        return admissionProduct;
    }

    @Override
    public void updateAdmissionProduct(String admissionId,String productCode,int balanceValue,int balanceValueDoc,boolean marked) throws Exception {
        AdmissionProductKey admissionProductKey=new AdmissionProductKey(admissionId,productCode);
        AdmissionProduct admissionProduct=admissionProductRepository.findById(admissionProductKey).orElseThrow(()->new Exception("Product not Found in Data Base"));
        admissionProduct.setBalanceValueAdmissionProducts(balanceValue);
        admissionProduct.setBalanceValueDoc(balanceValueDoc);
        admissionProduct.setMarking(marked);
        admissionProductRepository.save(admissionProduct);
    }

    @Override
    public void deleteById(AdmissionProductKey id){ admissionProductRepository.deleteById(id);
    }
}
