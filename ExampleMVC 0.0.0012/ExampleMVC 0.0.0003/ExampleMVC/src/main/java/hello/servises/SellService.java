package hello.servises;

import hello.model.Sell;
import hello.model.SellKey;
import hello.model.Sellings;
import hello.repository.AddSellRepository;
import hello.repository.SellRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

@Service
public class SellService implements ISellService {
    @Autowired
    private SellRepository sellRepository;
    @Autowired
    private AddSellRepository addSellRepository;

    @Override
    public Sellings findAll(){
        List<Sell> sellings=(List<Sell>)sellRepository.findAll();
        Sellings serSellings=new Sellings();
        serSellings.setSellings(sellings);
        return serSellings;
    }

    @Override
    public void insertSell(Sell sell){
        addSellRepository.insertSell(sell);
    }

    @Override
    public Sell getSellById(SellKey id){
        Sell sell=sellRepository.findById(id).orElse(new Sell());
        return sell;
    }

    @Override
    public void updateSell(String sellId, String warehouseId, Date sellDate, Time sellTime) throws Exception {
        SellKey sellKey=new SellKey(sellId,warehouseId);
        Sell sell=sellRepository.findById(sellKey).orElseThrow(()->new Exception("Product not Found in Data Base"));
        sell.setSellDate(sellDate);
        sell.setSellTime(sellTime);
        sellRepository.save(sell);
    }

    @Override
    public void deleteById(SellKey id){ sellRepository.deleteById(id);
    }
}
