package hello.repository;

import hello.model.AdmissionMarkingProduct;
import hello.model.AdmissionMarkingProductKey;
import hello.model.SellMarkingProduct;
import hello.model.SellMarkingProductKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellMarkingProductRepository extends JpaRepository<SellMarkingProduct, SellMarkingProductKey> {
}
