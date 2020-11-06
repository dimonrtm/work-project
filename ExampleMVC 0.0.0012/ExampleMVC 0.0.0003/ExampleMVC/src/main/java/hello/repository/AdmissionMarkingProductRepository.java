package hello.repository;

import hello.model.AdmissionMarkingProduct;
import hello.model.AdmissionMarkingProductKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdmissionMarkingProductRepository extends JpaRepository<AdmissionMarkingProduct, AdmissionMarkingProductKey> {
}
