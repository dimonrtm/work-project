package hello.repository;

import hello.model.AdmissionProduct;
import hello.model.SellProduct;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
public class AddSellProductRepository {
    @PersistenceContext
    EntityManager entityManager;

    @Transactional
    public void insertSellProduct(SellProduct sellProduct){
        entityManager.persist(sellProduct);
    }
}
