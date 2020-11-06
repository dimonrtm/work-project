package hello.repository;

import hello.model.AdmissionProduct;
import hello.model.AdmissionProductKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdmissionProductRepository extends JpaRepository<AdmissionProduct, AdmissionProductKey> {
}
