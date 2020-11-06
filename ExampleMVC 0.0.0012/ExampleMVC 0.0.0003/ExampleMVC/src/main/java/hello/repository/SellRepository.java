package hello.repository;

import hello.model.Admision;
import hello.model.AdmissionKey;
import hello.model.Sell;
import hello.model.SellKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellRepository extends JpaRepository<Sell, SellKey> {
}
