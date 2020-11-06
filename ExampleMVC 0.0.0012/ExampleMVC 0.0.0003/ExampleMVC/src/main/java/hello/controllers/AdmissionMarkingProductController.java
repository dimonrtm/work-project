package hello.controllers;

import hello.model.*;
import hello.servises.IAdmissionMarkingProductServise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
public class AdmissionMarkingProductController {
    @Autowired
    IAdmissionMarkingProductServise admissionMarkingProductService;

    @GetMapping(value = "/admissionMarkingProducts", produces = MediaType.APPLICATION_XML_VALUE)
    public @ResponseBody
    AdmissionMarkingProducts showAdmissionMarkingProducts() {
        return admissionMarkingProductService.findAll();
    }

    @RequestMapping(value = "/admissionMarkingProducts/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_XML_VALUE)
    public @ResponseBody
    Result addAdmissionMarkingProduct(@RequestParam("admission_marking_product_id") String admissionMarkingProductId,
                                      @RequestParam("admission_id") String admissionId,
                                      @RequestParam("product_code") String productCode,
                                      @RequestParam("barcode_labeling") String barcodeLabeling,
                                      @RequestParam("marking_completed") String markingCompleted) throws IOException {
        boolean completed = Boolean.parseBoolean(markingCompleted);
        AdmissionMarkingProduct admissionMarkingProduct = new AdmissionMarkingProduct(admissionMarkingProductId, admissionId, productCode, barcodeLabeling, completed);
        admissionMarkingProductService.insertAdmissionMarkingProduct(admissionMarkingProduct);
        return new Result("Add AdmissionMarkedProduct");
    }

    @GetMapping(value = "/admissionMarkingProducts/{admission_marking_product_id}/{admission_id}/{product_code}", produces = MediaType.APPLICATION_XML_VALUE)
    public @ResponseBody
    AdmissionMarkingProduct showAdmissionMarkingProductById(@PathVariable("admission_marking_product_id") String admissionMarkingProductId,
                                                            @PathVariable("admission_id") String admissionId,
                                                            @PathVariable("product_code") String productCode) throws IOException {
        AdmissionMarkingProductKey admissionMarkingProductKey = new AdmissionMarkingProductKey(admissionMarkingProductId, admissionId, productCode);
        return admissionMarkingProductService.getAdmissionMarkingProductById(admissionMarkingProductKey);
    }

    @RequestMapping(value = "/admissionMarkingProducts/{admission_marking_product_id}/{admission_id}/{product_code}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_XML_VALUE)
    public @ResponseBody
    Result updateAdmissionMarkingProduct(@PathVariable("admission_marking_product_id") String admissionMarkingProductId,
                                         @PathVariable("admission_id") String admissionId,
                                         @PathVariable("product_code") String productCode,
                                         @RequestParam("barcode_labeling") String barcodeLabeling,
                                         @RequestParam("marking_completed") String markingCompleted) throws Exception {
        boolean completed = Boolean.parseBoolean(markingCompleted);
        admissionMarkingProductService.updateAdmissionMarkingProduct(admissionMarkingProductId, admissionId, productCode, barcodeLabeling, completed);
        return new Result("AdmisssionMarkingProduct update");
    }

    @RequestMapping(value = "/admissionMarkingProducts/{admission_marking_product_id}/{admission_id}/{product_code}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_XML_VALUE)
    public @ResponseBody
    Result deleteAdmissionProduct(@PathVariable("admission_marking_product_id") String admissionMarkingProductId,
                                  @PathVariable("admission_id") String admissionId,
                                  @PathVariable("product_code") String productCode) throws IOException {
        AdmissionMarkingProductKey admissionMarkingProductKey = new AdmissionMarkingProductKey(admissionMarkingProductId,admissionId, productCode);
        admissionMarkingProductService.deleteById(admissionMarkingProductKey);
        return new Result("AdmissionMarkingProduct Delete");
    }
}
