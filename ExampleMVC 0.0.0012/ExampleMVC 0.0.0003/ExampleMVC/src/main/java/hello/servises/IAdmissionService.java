package hello.servises;

import hello.model.*;

import java.sql.Date;
import java.sql.Time;

public interface IAdmissionService {
    Admissions findAll();
    void insertAdmission(Admision admission);
    Admision getAdmissionById(AdmissionKey id);
    void updateAdmission(String admissionId, String warehouseId, Date admissionDate, Time admissionTime) throws Exception;
    void deleteById(AdmissionKey id);
}
