package hello.controllers;

import hello.model.*;
import hello.servises.IAdmissionProductService;
import hello.servises.ISellProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
public class SellProductController {
    @Autowired
    ISellProductService sellProductService;

    @GetMapping(value = "/sellProducts", produces = MediaType.APPLICATION_XML_VALUE)
    public @ResponseBody
    SellProducts showSellProducts() {
        return sellProductService.findAll();
    }

    @RequestMapping(value = "/sellProducts/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_XML_VALUE)
    public @ResponseBody
    Result addProduct(@RequestParam("sell_id") String sellId,
                      @RequestParam("product_code") String productCode,
                      @RequestParam("balance_value") String balanceValue,
                      @RequestParam("balance_value_doc") String balanceValueDoc,
                      @RequestParam("marking") String marking) throws IOException {
        int valueBalance = Integer.parseInt(balanceValue);
        int balanceDocValue = Integer.parseInt(balanceValueDoc);
        boolean mark = Boolean.parseBoolean(marking);
        SellProduct sellProduct = new SellProduct(sellId, productCode, valueBalance, balanceDocValue, mark);
        sellProductService.insertSellProduct(sellProduct);
        return new Result("Add SellProducts");
    }

    @GetMapping(value = "/sellProducts/{sell_id}/{product_code}", produces = MediaType.APPLICATION_XML_VALUE)
    public @ResponseBody
    SellProduct showSellProductById(@PathVariable("sell_id") String sellId,
                                              @PathVariable("product_code") String productCode) throws IOException {
        SellProductKey sellProductKey = new SellProductKey(sellId, productCode);
        return sellProductService.getSellProductById(sellProductKey);
    }

    @RequestMapping(value = "/sellProducts/{sell_id}/{product_code}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_XML_VALUE)
    public @ResponseBody
    Result updateAdmissionProduct(@PathVariable("sell_id") String sellId,
                                  @PathVariable("product_code") String productCode,
                                  @RequestParam("balance_value") String balanceValue,
                                  @RequestParam("balance_value_doc") String balanceValueDoc,
                                  @RequestParam("marking") String marking) throws Exception {
        int valueBalance = Integer.parseInt(balanceValue);
        int docBalanceValue = Integer.parseInt(balanceValueDoc);
        boolean marked = Boolean.parseBoolean(marking);
        sellProductService.updateSellProduct(sellId, productCode, valueBalance, docBalanceValue, marked);
        return new Result("CellProduct update");
    }

    @RequestMapping(value = "/sellProducts/{sell_id}/{product_code}", method = RequestMethod.DELETE,produces=MediaType.APPLICATION_XML_VALUE)
    public @ResponseBody Result deleteSellProduct(@PathVariable("sell_id") String sellId,
                                                       @PathVariable("product_code") String productCode) throws IOException {
        SellProductKey sellProductKey=new SellProductKey(sellId,productCode);
        sellProductService.deleteById(sellProductKey);
        return new Result("SellProduct Delete");
    }
}
