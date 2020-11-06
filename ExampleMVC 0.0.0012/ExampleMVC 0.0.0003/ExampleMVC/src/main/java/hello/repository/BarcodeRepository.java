package hello.repository;

import hello.model.Barcode;
import org.springframework.data.repository.CrudRepository;

public interface BarcodeRepository extends CrudRepository<Barcode,String> {
}
