package hello.repository;

import hello.model.AdmissionProduct;
import hello.model.AdmissionProductKey;
import hello.model.SellProduct;
import hello.model.SellProductKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellProductRepository extends JpaRepository<SellProduct, SellProductKey> {
}
