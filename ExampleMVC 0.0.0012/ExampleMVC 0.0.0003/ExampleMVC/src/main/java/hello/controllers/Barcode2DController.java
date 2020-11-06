package hello.controllers;

import hello.model.*;
import hello.servises.IBarcode2DService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
public class Barcode2DController {
    @Autowired
    IBarcode2DService barcode2DService;

    @GetMapping(value = "/barcodes2D", produces = MediaType.APPLICATION_XML_VALUE)
    public @ResponseBody
    Barcodes2D showBarcodes2D() {
        return barcode2DService.findAll();
    }

    @RequestMapping(value = "/barcodes2D/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_XML_VALUE)
    public @ResponseBody
    Result addBarcode2D(@RequestParam("barcode_value") String barcodeValue,
                        @RequestParam("product_code") String productCode) throws IOException {
        Barcode2D barcode2D = new Barcode2D(barcodeValue, productCode);
        barcode2DService.insertBarcode2D(barcode2D);
        return new Result("Add Barcode2D");
    }

    @GetMapping(value="/barcodes2D/{barcode_value}",produces=MediaType.APPLICATION_XML_VALUE)
    public @ResponseBody Barcode2D showBarcodeById(@PathVariable("barcode_value") String barcodeValue) throws IOException {
        return barcode2DService.getBarcode2DById(barcodeValue);
    }

    @RequestMapping(value="/barcodes2D/{barcode_value}",method=RequestMethod.PUT,produces=MediaType.APPLICATION_XML_VALUE)
    public @ResponseBody Result updateBarcode2D(@PathVariable("barcode_value") String barcodeValue,
                                              @RequestParam("product_code") String productCode) throws Exception {
        barcode2DService.updateBarcode2D(barcodeValue,productCode);
        return new Result("Barcode2D update");
    }

    @RequestMapping(value = "/barcodes2D/{barcode_value}", method = RequestMethod.DELETE,produces=MediaType.APPLICATION_XML_VALUE)
    public @ResponseBody Result deleteBarcode2D(@PathVariable("barcode_value") String barcodeValue) throws IOException {
        barcode2DService.deleteById(barcodeValue);
        return new Result("Barcode2D Delete");
    }
}
