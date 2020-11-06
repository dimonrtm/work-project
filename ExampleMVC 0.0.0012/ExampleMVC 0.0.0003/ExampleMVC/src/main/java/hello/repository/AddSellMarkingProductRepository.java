package hello.repository;

import hello.model.AdmissionMarkingProduct;
import hello.model.SellMarkingProduct;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
public class AddSellMarkingProductRepository {
    @PersistenceContext
    EntityManager entityManager;

    @Transactional
    public void insertSellMarkingProduct(SellMarkingProduct sellMarkingProduct){
        entityManager.persist(sellMarkingProduct);
    }
}
