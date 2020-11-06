package hello.controllers;

import hello.model.*;
import hello.servises.IBarcodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
public class BarcodeController {

    @Autowired
    IBarcodeService barcodeService;

    @GetMapping(value="/barcodes",produces= MediaType.APPLICATION_XML_VALUE)
    public @ResponseBody
    Barcodes showBarcodes() {
        return barcodeService.findAll();
    }

    @RequestMapping(value = "/barcodes/add", method = RequestMethod.POST,produces=MediaType.APPLICATION_XML_VALUE)
    public @ResponseBody
    Result addBarcode(@RequestParam("barcode_value") String barcodeValue,
                      @RequestParam("product_code") String productCode) throws IOException {
        Barcode barcode =new Barcode(barcodeValue,productCode);
        barcodeService.insertBarcode(barcode);
        return new Result("Add Barcode");
    }

    @GetMapping(value="/barcodes/{barcode_value}",produces=MediaType.APPLICATION_XML_VALUE)
    public @ResponseBody Barcode showBarcodeById(@PathVariable("barcode_value") String barcodeValue) throws IOException {
        return barcodeService.getBarcodeById(barcodeValue);
    }

    @RequestMapping(value="/barcodes/{barcode_value}",method=RequestMethod.PUT,produces=MediaType.APPLICATION_XML_VALUE)
    public @ResponseBody Result updateBarcode(@PathVariable("barcode_value") String barcodeValue,
                                           @RequestParam("product_code") String productCode) throws Exception {
        barcodeService.updateBarcode(barcodeValue,productCode);
        return new Result("Barcode update");
    }

    @RequestMapping(value = "/barcodes/{barcode_value}", method = RequestMethod.DELETE,produces=MediaType.APPLICATION_XML_VALUE)
    public @ResponseBody Result deleteBarcode(@PathVariable("barcode_value") String barcodeValue) throws IOException {
        barcodeService.deleteById(barcodeValue);
        return new Result("Barcode Delete");
    }
}
