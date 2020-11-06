package hello.repository;

import hello.model.Admision;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
public class AddAdmissionRepository {
    @PersistenceContext
    EntityManager entityManager;

    @Transactional
    public void insertAdmission(Admision admision){
        this.entityManager.persist(admision);
    }
}
