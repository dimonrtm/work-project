package hello.repository;

import hello.model.Balance;
import hello.model.BalanceKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BalanceRepository extends JpaRepository<Balance, BalanceKey> {
    List<Balance> findByProductCode(String productCode);
    List<Balance> findByWarehouseCode(String warehouseCode);
}
