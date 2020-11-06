package hello.servises;

import hello.model.Product;
import hello.model.Products;
import hello.model.Result;
import hello.model.User;
import hello.repository.AddProductRepository;
import hello.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;

@Service
public class ProductService implements IProductService{
    @Autowired
    ProductRepository productRepository;
    @Autowired
    AddProductRepository addProductRepository;

    @Override
    public Products findAll(){
        List<Product> products=(List<Product>)productRepository.findAll();
        Products serProducts=new Products();
        serProducts.setProducts(products);
        return serProducts;
    }

    @Override
    public void insertProduct(Product product){
        addProductRepository.insertProduct(product);
    }

    @Override
    public void deleteById(String id){
        productRepository.deleteById(id);
    }

    @Override
    public Product getProductById(String id){
        Product product=productRepository.findById(id).orElse(new Product());
        return product;
    }

    @Override
    public void updateProduct(String productCode,String vendorCode,String productName) throws Exception {
        Product product=productRepository.findById(productCode).orElseThrow(()->new Exception("Product not Found in Data Base"));
        product.setVendorCode(vendorCode);
        product.setProductName(productName);
        productRepository.save(product);
    }
}
