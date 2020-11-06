package hello.repository;

import hello.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
public class AddUserRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void insertUser(User user){
        this.entityManager.persist(user);
    }
}
