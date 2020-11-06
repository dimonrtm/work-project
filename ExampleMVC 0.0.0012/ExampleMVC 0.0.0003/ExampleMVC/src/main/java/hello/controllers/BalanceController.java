package hello.controllers;

import hello.model.*;
import hello.servises.IBalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
public class BalanceController {
    @Autowired
    IBalanceService balanceService;

    @GetMapping(value = "/balances", produces = MediaType.APPLICATION_XML_VALUE)
    public @ResponseBody
    Balances showBalances() {
        return balanceService.findAll();
    }

    @RequestMapping(value = "/balances/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_XML_VALUE)
    public @ResponseBody
    Result addProduct(@RequestParam("product_code") String productCode,
                      @RequestParam("warehouse_code") String warehouseCode,
                      @RequestParam("balance_value") String balanceValue) throws IOException {
        double valueBalance = Double.parseDouble(balanceValue);
        Balance balance = new Balance(productCode, warehouseCode, valueBalance);
        balanceService.insertBalance(balance);
        return new Result("Add Balance");
    }

    @GetMapping(value = "/balances/{product_code}/{warehouse_code}", produces = MediaType.APPLICATION_XML_VALUE)
    public @ResponseBody
    Balance showBalanceById(@PathVariable("product_code") String productCode,
                            @PathVariable("warehouse_code") String warehouseCode) throws IOException {
        BalanceKey balanceKey = new BalanceKey(productCode, warehouseCode);
        return balanceService.getBalanceById(balanceKey);
    }

    @RequestMapping(value = "/balances/{product_code}/{warehouse_code}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_XML_VALUE)
    public @ResponseBody
    Result updateUser(@PathVariable("product_code") String productCode,
                      @PathVariable("warehouse_code") String warehouseCode,
                      @RequestParam("balance_value") String balanceValue) throws Exception {
        double valueBalance=Double.parseDouble(balanceValue);
        balanceService.updateBalance(productCode, warehouseCode,valueBalance);
        return new Result("Balance update");
    }

    @RequestMapping(value = "/balances/{product_code}/{warehouse_code}", method = RequestMethod.DELETE,produces=MediaType.APPLICATION_XML_VALUE)
    public @ResponseBody Result deleteBalance(@PathVariable("product_code") String productCode,
                                              @PathVariable("warehouse_code") String warehouseCode) throws IOException {
        BalanceKey balanceKey=new BalanceKey(productCode,warehouseCode);
        balanceService.deleteById(balanceKey);
        return new Result("Balance Delete");
    }
}
