package hello.repository;

import hello.model.Balance;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
public class AddBalanceRepository {
    @PersistenceContext
    EntityManager entityManager;

    @Transactional
    public void insertBalance(Balance balance){
        this.entityManager.persist(balance);
    }
}
