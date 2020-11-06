package hello.servises;

import hello.model.*;

public interface IAdmissionProductService {
    AdmissionProducts findAll();
    void insertAdmissionProduct(AdmissionProduct admissionProduct);
    AdmissionProduct getAdmissionProductById(AdmissionProductKey id);
    void updateAdmissionProduct(String productCode,String warehouseCode,int balanceValue,int balanceValueDoc,boolean marked) throws Exception;
    void deleteById(AdmissionProductKey id);
}
