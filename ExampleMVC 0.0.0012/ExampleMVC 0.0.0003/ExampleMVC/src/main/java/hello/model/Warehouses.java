package hello.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Warehouses implements Serializable {
    private static final long serialVersionUID=27L;

    @JacksonXmlProperty(localName="Warehouse")
    @JacksonXmlElementWrapper(useWrapping=false)
    private List<Warehouse> warehouses=new ArrayList<Warehouse>();

    public List<Warehouse> getWarehouses() {
        return warehouses;
    }

    public void setWarehouses(List<Warehouse> warehouses) {
        this.warehouses = warehouses;
    }
}
