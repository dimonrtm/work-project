package hello.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Sellings implements Serializable {
    private static final long serialVersionUID=41L;

    @JacksonXmlProperty(localName = "Sell")
    @JacksonXmlElementWrapper(useWrapping=false)
    List<Sell> sellings=new ArrayList<Sell>();

    public List<Sell> getSellings() {
        return sellings;
    }

    public void setSellings(List<Sell> sellings) {
        this.sellings = sellings;
    }
}
