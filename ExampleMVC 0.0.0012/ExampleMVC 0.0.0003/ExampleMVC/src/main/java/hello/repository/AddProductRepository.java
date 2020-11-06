package hello.repository;

import hello.model.Product;
import hello.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
public class AddProductRepository {
    @PersistenceContext
    EntityManager entityManager;

    @Transactional
    public void insertProduct(Product product){
       this.entityManager.persist(product);
    }
}
