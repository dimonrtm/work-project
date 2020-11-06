package hello.repository;

import hello.model.Admision;
import hello.model.Sell;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
public class AddSellRepository {
    @PersistenceContext
    EntityManager entityManager;

    @Transactional
    public void insertSell(Sell sell){
        this.entityManager.persist(sell);
    }
}
