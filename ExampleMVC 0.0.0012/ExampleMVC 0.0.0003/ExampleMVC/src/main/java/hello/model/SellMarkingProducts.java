package hello.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SellMarkingProducts implements Serializable {
    private static final long serialVersionUID=45L;

    @JacksonXmlProperty(localName="SellMarkingProduct")
    @JacksonXmlElementWrapper(useWrapping=false)
    List<SellMarkingProduct> sellMarkingProducts=new ArrayList<SellMarkingProduct>();

    public List<SellMarkingProduct> getSellMarkingProducts() {
        return sellMarkingProducts;
    }

    public void setSellMarkingProducts(List<SellMarkingProduct> sellMarkingProducts) {
        this.sellMarkingProducts = sellMarkingProducts;
    }
}
