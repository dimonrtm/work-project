package hello.repository;

import hello.model.Admision;
import hello.model.AdmissionKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdmissonRepository extends JpaRepository<Admision, AdmissionKey> {
}
