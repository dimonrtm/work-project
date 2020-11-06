package hello.repository;

import hello.model.AdmissionMarkingProduct;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
public class AddAdmissionMarkingProductRepository {
    @PersistenceContext
    EntityManager entityManager;

    @Transactional
    public void insertAdmissionMarkingProduct(AdmissionMarkingProduct admissionMarkingProduct){
        entityManager.persist(admissionMarkingProduct);
    }
}
