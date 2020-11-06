package hello.controllers;

import hello.model.*;
import hello.servises.IAdmissionMarkingProductServise;
import hello.servises.ISellMarkingProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
public class SellMarkingProductController {
    @Autowired
    ISellMarkingProductService sellMarkingProductService;

    @GetMapping(value = "/sellMarkingProducts", produces = MediaType.APPLICATION_XML_VALUE)
    public @ResponseBody
    SellMarkingProducts showSellMarkingProducts() {
        return sellMarkingProductService.findAll();
    }

    @RequestMapping(value = "/sellMarkingProducts/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_XML_VALUE)
    public @ResponseBody
    Result addSellMarkingProduct(@RequestParam("sell_marking_product_id") String sellMarkingProductId,
                                      @RequestParam("sell_id") String sellId,
                                      @RequestParam("product_code") String productCode,
                                      @RequestParam("barcode_labeling") String barcodeLabeling,
                                      @RequestParam("marking_completed") String markingCompleted) throws IOException {
        boolean completed = Boolean.parseBoolean(markingCompleted);
        SellMarkingProduct sellMarkingProduct = new SellMarkingProduct(sellMarkingProductId, sellId, productCode, barcodeLabeling, completed);
        sellMarkingProductService.insertSellMarkingProduct(sellMarkingProduct);
        return new Result("Add SellMarkedProduct");
    }

    @GetMapping(value = "/sellMarkingProducts/{sell_marking_product_id}/{sell_id}/{product_code}", produces = MediaType.APPLICATION_XML_VALUE)
    public @ResponseBody
    SellMarkingProduct showSellMarkingProductById(@PathVariable("sell_marking_product_id") String sellMarkingProductId,
                                                            @PathVariable("sell_id") String sellId,
                                                            @PathVariable("product_code") String productCode) throws IOException {
        SellMarkingProductKey sellMarkingProductKey = new SellMarkingProductKey(sellMarkingProductId, sellId, productCode);
        return sellMarkingProductService.getSellMarkingProductById(sellMarkingProductKey);
    }

    @RequestMapping(value = "/sellMarkingProducts/{sell_marking_product_id}/{sell_id}/{product_code}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_XML_VALUE)
    public @ResponseBody
    Result updateSellMarkingProduct(@PathVariable("sell_marking_product_id") String sellMarkingProductId,
                                         @PathVariable("sell_id") String sellId,
                                         @PathVariable("product_code") String productCode,
                                         @RequestParam("barcode_labeling") String barcodeLabeling,
                                         @RequestParam("marking_completed") String markingCompleted) throws Exception {
        boolean completed = Boolean.parseBoolean(markingCompleted);
        sellMarkingProductService.updateSellMarkingProduct(sellMarkingProductId, sellId, productCode, barcodeLabeling, completed);
        return new Result("SellMarkingProduct update");
    }

    @RequestMapping(value = "/sellMarkingProducts/{sell_marking_product_id}/{sell_id}/{product_code}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_XML_VALUE)
    public @ResponseBody
    Result deleteSellProduct(@PathVariable("sell_marking_product_id") String sellMarkingProductId,
                                  @PathVariable("sell_id") String sellId,
                                  @PathVariable("product_code") String productCode) throws IOException {
        SellMarkingProductKey sellMarkingProductKey = new SellMarkingProductKey(sellMarkingProductId,sellId, productCode);
        sellMarkingProductService.deleteById(sellMarkingProductKey);
        return new Result("SellMarkingProduct Delete");
    }
}
