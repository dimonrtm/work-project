package hello.repository;

import hello.model.Barcode;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
public class AddBarcodeRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Transactional
    public void insertBarcode(Barcode barcode){
        this.entityManager.persist(barcode);
    }
}
