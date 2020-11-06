package hello.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Barcodes implements Serializable {
    private static final long serialVersionUID=29L;

    @JacksonXmlProperty(localName="Barcode")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Barcode> barcodes=new ArrayList<Barcode>();

    public List<Barcode> getBarcodes() {
        return barcodes;
    }

    public void setBarcodes(List<Barcode> barcodes) {
        this.barcodes = barcodes;
    }
}
