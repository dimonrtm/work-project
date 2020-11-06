package hello.repository;

import hello.model.AdmissionProduct;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
public class AddAdmissionProductRepository {
    @PersistenceContext
    EntityManager entityManager;

    @Transactional
    public void insertAdmissionProduct(AdmissionProduct admissionProduct){
        entityManager.persist(admissionProduct);
    }
}
