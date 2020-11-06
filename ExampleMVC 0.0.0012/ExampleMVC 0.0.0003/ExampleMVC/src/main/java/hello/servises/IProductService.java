package hello.servises;

import hello.model.Product;
import hello.model.Products;

import java.util.List;

public interface IProductService {
    Products findAll();
    void insertProduct(Product product);
    void deleteById(String id);
    Product getProductById(String id);
    void updateProduct(String productCode,String vendorCode,String productName) throws Exception;
}
