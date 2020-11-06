package hello.controllers;

import hello.model.*;
import hello.servises.IAdmissionService;
import hello.servises.ISellService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Controller
public class SellController {
    @Autowired
    ISellService sellService;

    @GetMapping(value = "/sellings", produces = MediaType.APPLICATION_XML_VALUE)
    public @ResponseBody
    Sellings showSellings() {
        return sellService.findAll();
    }

    @RequestMapping(value = "/sellings/addXmlFormatData", method = RequestMethod.POST, produces = MediaType.APPLICATION_XML_VALUE)
    public @ResponseBody
    Result addSellXmlFormatData(@RequestParam("sell_id") String sellId,
                                     @RequestParam("warehouse_id") String warehouseId,
                                     @RequestParam("sell_date") String sellDate,
                                     @RequestParam("sell_time") String sellTime) throws IOException, ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        java.util.Date slDateUtil = sdf.parse(sellDate);
        Date slDate = new Date(slDateUtil.getTime());
        Time slTime = Time.valueOf(sellTime);
        Sell sell = new Sell(sellId, warehouseId, slDate, slTime);
        sellService.insertSell(sell);
        return new Result("Add Sell");
    }

    @RequestMapping(value = "/sellings/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_XML_VALUE)
    public @ResponseBody
    Result addAdmission(@RequestParam("sell_id") String sellId,
                        @RequestParam("warehouse_id") String warehouseId,
                        @RequestParam("sell_date") String sellDate,
                        @RequestParam("sell_time") String sellTime) throws IOException, ParseException {
        Date slDate = Date.valueOf(sellDate);
        Time slTime = Time.valueOf(sellTime);
        Sell sell = new Sell(sellId, warehouseId, slDate, slTime);
        sellService.insertSell(sell);
        return new Result("Add Sell");
    }

    @GetMapping(value = "/sellings/{sell_id}/{warehouse_id}", produces = MediaType.APPLICATION_XML_VALUE)
    public @ResponseBody
    Sell showSellById(@PathVariable("sell_id") String sellId,
                               @PathVariable("warehouse_id") String warehouseId) throws IOException {
        SellKey sellKey = new SellKey(sellId, warehouseId);
        return sellService.getSellById(sellKey);
    }

    @RequestMapping(value = "/sellings/{sell_id}/{warehouse_id}/XmlFormat", method = RequestMethod.PUT, produces = MediaType.APPLICATION_XML_VALUE)
    public @ResponseBody
    Result updateSellXmlFormat(@PathVariable("sell_id") String sellId,
                                    @PathVariable("warehouse_id") String warehouseId,
                                    @RequestParam("sell_date") String sellDate,
                                    @RequestParam("sell_time") String sellTime) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        java.util.Date slDateUtil = sdf.parse(sellDate);
        Date slDate = new Date(slDateUtil.getTime());
        Time slTime = Time.valueOf(sellTime);
        sellService.updateSell(sellId, warehouseId, slDate, slTime);
        return new Result("Sell update");
    }

    @RequestMapping(value = "/sellings/{sell_id}/{warehouse_id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_XML_VALUE)
    public @ResponseBody
    Result updateSell(@PathVariable("sell_id") String sellId,
                           @PathVariable("warehouse_id") String warehouseId,
                           @RequestParam("sell_date") String sellDate,
                           @RequestParam("sell_time") String sellTime) throws Exception {
        Date slDate = Date.valueOf(sellDate);
        Time slTime = Time.valueOf(sellTime);
        sellService.updateSell(sellId, warehouseId, slDate, slTime);
        return new Result("Sell update");
    }

    @RequestMapping(value = "/sellings/{sell_id}/{warehouse_id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_XML_VALUE)
    public @ResponseBody
    Result deleteSell(@PathVariable("sell_id") String sellId,
                           @PathVariable("warehouse_id") String warehouseId) throws IOException {
        SellKey sellKey = new SellKey(sellId, warehouseId);
        sellService.deleteById(sellKey);
        return new Result("Sell Delete");
    }
}
