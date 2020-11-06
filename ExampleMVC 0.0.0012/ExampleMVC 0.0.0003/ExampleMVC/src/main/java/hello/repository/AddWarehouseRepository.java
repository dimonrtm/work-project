package hello.repository;

import hello.model.Warehouse;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
public class AddWarehouseRepository {
    @PersistenceContext
    EntityManager entityManager;

    @Transactional
    public void insertWarehouse(Warehouse warehouse){
        this.entityManager.persist(warehouse);
    }
}
