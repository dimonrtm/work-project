package hello.servises;

import hello.model.*;

public interface IBalanceService {
    Balances findAll();
    void insertBalance(Balance balance);
    void deleteById(BalanceKey id);
    Balance getBalanceById(BalanceKey id);
    void updateBalance(String productCode,String warehouseCode,double balanceValue) throws Exception;
//    Balances findByBalanceKeyProductId(String productId);
//    Balances findByBalanceKeyWarehouseId(String warehouseId);
}
