package hello.servises;

import hello.model.*;

import java.sql.Date;
import java.sql.Time;

public interface ISellService {
    Sellings findAll();
    void insertSell(Sell sell);
    Sell getSellById(SellKey id);
    void updateSell(String sellId, String warehouseId, Date sellDate, Time sellTime) throws Exception;
    void deleteById(SellKey id);
}
