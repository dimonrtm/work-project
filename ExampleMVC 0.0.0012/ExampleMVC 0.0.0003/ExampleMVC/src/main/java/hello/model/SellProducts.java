package hello.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SellProducts implements Serializable {
    private static final long serialVersionUID=43L;

    @JacksonXmlProperty(localName = "SellProduct")
    @JacksonXmlElementWrapper(useWrapping=false)
    List<SellProduct> sellProducts=new ArrayList<SellProduct>();

    public List<SellProduct> getSellProducts() {
        return sellProducts;
    }

    public void setSellProducts(List<SellProduct> sellProducts) {
        this.sellProducts = sellProducts;
    }
}
