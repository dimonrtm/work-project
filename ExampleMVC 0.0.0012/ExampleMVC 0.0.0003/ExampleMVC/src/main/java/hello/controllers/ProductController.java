package hello.controllers;

import hello.model.*;
import hello.servises.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
public class ProductController {

    @Autowired
    IProductService productService;

    @GetMapping(value="/products",produces= MediaType.APPLICATION_XML_VALUE)
    public @ResponseBody Products showProducts() {
        return productService.findAll();
    }

    @RequestMapping(value = "/products/add", method = RequestMethod.POST,produces=MediaType.APPLICATION_XML_VALUE)
    public @ResponseBody Result addProduct(@RequestParam("product_code") String productCode,
                                        @RequestParam("vendor_code") String vendorCode,
                                        @RequestParam("product_name") String productName) throws IOException {
        Product product =new Product(productCode,vendorCode,productName);
        productService.insertProduct(product);
        return new Result("Add Product");
    }

    @GetMapping(value="/products/{product_code}",produces=MediaType.APPLICATION_XML_VALUE)
    public @ResponseBody Product showProductById(@PathVariable("product_code") String productCode) throws IOException {
        return productService.getProductById(productCode);
    }

    @RequestMapping(value="/products/{product_code}",method=RequestMethod.PUT,produces=MediaType.APPLICATION_XML_VALUE)
    public @ResponseBody Result updateUser(@PathVariable("product_code") String productCode,
                                           @RequestParam("vendor_code") String vendorCode,
                                           @RequestParam("product_name") String productName) throws Exception {
        productService.updateProduct(productCode,vendorCode,productName);
        return new Result("Product update");
    }

    @RequestMapping(value = "/products/{product_code}", method = RequestMethod.DELETE,produces=MediaType.APPLICATION_XML_VALUE)
    public @ResponseBody Result deleteProduct(@PathVariable("product_code") String productCode) throws IOException {
        productService.deleteById(productCode);
        return new Result("Product Delete");
    }
}
