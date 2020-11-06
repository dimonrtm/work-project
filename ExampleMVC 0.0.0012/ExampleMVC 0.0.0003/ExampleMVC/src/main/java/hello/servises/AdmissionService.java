package hello.servises;

import hello.model.*;
import hello.repository.AddAdmissionRepository;
import hello.repository.AdmissonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

@Service
public class AdmissionService implements IAdmissionService{
    @Autowired
    private AdmissonRepository admissionRepository;
    @Autowired
    private AddAdmissionRepository addAdmissionRepository;

    @Override
    public Admissions findAll(){
        List<Admision> admissions=(List<Admision>)admissionRepository.findAll();
        Admissions serAdmissions=new Admissions();
        serAdmissions.setAdmissions(admissions);
        return serAdmissions;
    }

    @Override
    public void insertAdmission(Admision admission){
        addAdmissionRepository.insertAdmission(admission);
    }

    @Override
    public Admision getAdmissionById(AdmissionKey id){
        Admision admission=admissionRepository.findById(id).orElse(new Admision());
        return admission;
    }

    @Override
    public void updateAdmission(String admissionId, String warehouseId, Date admissionDate, Time admissionTime) throws Exception {
        AdmissionKey admissionKey=new AdmissionKey(admissionId,warehouseId);
        Admision admission=admissionRepository.findById(admissionKey).orElseThrow(()->new Exception("Product not Found in Data Base"));
        admission.setAdmissionDate(admissionDate);
        admission.setAdmissionTime(admissionTime);
        admissionRepository.save(admission);
    }

    @Override
    public void deleteById(AdmissionKey id){ admissionRepository.deleteById(id);
    }
}
