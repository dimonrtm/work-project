package hello.repository;

import hello.model.Barcode2D;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
public class AddBarcode2DRepositiory {

    @PersistenceContext
    EntityManager entityManager;

    @Transactional
    public void insertBarcode2D(Barcode2D barcode2D){
        this.entityManager.persist(barcode2D);
    }
}
