package hello.servises;

import hello.model.*;

public interface IAdmissionMarkingProductServise {
    AdmissionMarkingProducts findAll();
    void insertAdmissionMarkingProduct(AdmissionMarkingProduct admissionMarkingProduct);
    AdmissionMarkingProduct getAdmissionMarkingProductById(AdmissionMarkingProductKey id);
    void updateAdmissionMarkingProduct(String admissionMarkingProductId,String admissionId,String productCode,String barcodeLabeling,boolean marking_completed) throws Exception;
    void deleteById(AdmissionMarkingProductKey id);
}
