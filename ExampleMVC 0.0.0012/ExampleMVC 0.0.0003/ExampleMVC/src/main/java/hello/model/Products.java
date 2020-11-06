package hello.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Products implements Serializable {
    private static final long serialVersionUID=25L;

    @JacksonXmlProperty(localName="Product")
    @JacksonXmlElementWrapper(useWrapping=false)
    private List<Product> products=new ArrayList<Product>();

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
