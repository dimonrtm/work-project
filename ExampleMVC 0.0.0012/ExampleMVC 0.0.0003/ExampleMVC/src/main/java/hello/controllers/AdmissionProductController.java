package hello.controllers;

import hello.model.*;
import hello.servises.IAdmissionProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
public class AdmissionProductController {
    @Autowired
    IAdmissionProductService admissionProductService;

    @GetMapping(value = "/admissionProducts", produces = MediaType.APPLICATION_XML_VALUE)
    public @ResponseBody
    AdmissionProducts showAdmissionProducts() {
        return admissionProductService.findAll();
    }

    @RequestMapping(value = "/admissionProducts/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_XML_VALUE)
    public @ResponseBody
    Result addProduct(@RequestParam("admission_id") String admissionId,
                      @RequestParam("product_code") String productCode,
                      @RequestParam("balance_value") String balanceValue,
                      @RequestParam("balance_value_doc") String balanceValueDoc,
                      @RequestParam("marking") String marking) throws IOException {
        int valueBalance = Integer.parseInt(balanceValue);
        int balanceDocValue = Integer.parseInt(balanceValueDoc);
        boolean mark = Boolean.parseBoolean(marking);
        AdmissionProduct admissionProduct = new AdmissionProduct(admissionId, productCode, valueBalance, balanceDocValue, mark);
        admissionProductService.insertAdmissionProduct(admissionProduct);
        return new Result("Add AdmissionProducts");
    }

    @GetMapping(value = "/admissionProducts/{admission_id}/{product_code}", produces = MediaType.APPLICATION_XML_VALUE)
    public @ResponseBody
    AdmissionProduct showAdmissionProductById(@PathVariable("admission_id") String admissionId,
                                              @PathVariable("product_code") String productCode) throws IOException {
        AdmissionProductKey admissionProductKey = new AdmissionProductKey(admissionId, productCode);
        return admissionProductService.getAdmissionProductById(admissionProductKey);
    }

    @RequestMapping(value = "/admissionProducts/{admission_id}/{product_code}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_XML_VALUE)
    public @ResponseBody
    Result updateAdmissionProduct(@PathVariable("admission_id") String admissionId,
                                  @PathVariable("product_code") String productCode,
                                  @RequestParam("balance_value") String balanceValue,
                                  @RequestParam("balance_value_doc") String balanceValueDoc,
                                  @RequestParam("marking") String marking) throws Exception {
        int valueBalance = Integer.parseInt(balanceValue);
        int docBalanceValue = Integer.parseInt(balanceValueDoc);
        boolean marked = Boolean.parseBoolean(marking);
        admissionProductService.updateAdmissionProduct(admissionId, productCode, valueBalance, docBalanceValue, marked);
        return new Result("AdmisssionProduct update");
    }

    @RequestMapping(value = "/admissionProducts/{admission_id}/{product_code}", method = RequestMethod.DELETE,produces=MediaType.APPLICATION_XML_VALUE)
    public @ResponseBody Result deleteAdmissionProduct(@PathVariable("admission_id") String admissionId,
                                              @PathVariable("product_code") String productCode) throws IOException {
        AdmissionProductKey admissionProductKey=new AdmissionProductKey(admissionId,productCode);
        admissionProductService.deleteById(admissionProductKey);
        return new Result("AdmissionProduct Delete");
    }
}
