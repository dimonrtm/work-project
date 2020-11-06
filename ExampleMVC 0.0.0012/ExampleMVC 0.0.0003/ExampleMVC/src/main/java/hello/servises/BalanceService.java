package hello.servises;

import hello.model.*;
import hello.repository.AddBalanceRepository;
import hello.repository.BalanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BalanceService implements IBalanceService{
    @Autowired
    BalanceRepository balanceRepository;
    @Autowired
    AddBalanceRepository addBalanceRepository;

    @Override
    public Balances findAll(){
        List<Balance> balances=(List<Balance>)balanceRepository.findAll();
        Balances serBalances=new Balances();
        serBalances.setBalances(balances);
        return serBalances;
    }

    @Override
    public void insertBalance(Balance balance){
        addBalanceRepository.insertBalance(balance);
    }

    @Override
    public Balance getBalanceById(BalanceKey id){
        Balance balance=balanceRepository.findById(id).orElse(new Balance());
        return balance;
    }

    @Override
    public void deleteById(BalanceKey id){ balanceRepository.deleteById(id);
    }

    @Override
    public void updateBalance(String productCode,String warehouseCode,double balanceValue) throws Exception {
        BalanceKey balanceKey=new BalanceKey(productCode,warehouseCode);
        Balance balance=balanceRepository.findById(balanceKey).orElseThrow(()->new Exception("Product not Found in Data Base"));
        balance.setBalanceValue(balanceValue);
        balanceRepository.save(balance);
    }
}
