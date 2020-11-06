package hello.repository;

import hello.model.Barcode2D;
import org.springframework.data.repository.CrudRepository;

public interface Barcode2DRepository extends CrudRepository<Barcode2D,String> {
}
