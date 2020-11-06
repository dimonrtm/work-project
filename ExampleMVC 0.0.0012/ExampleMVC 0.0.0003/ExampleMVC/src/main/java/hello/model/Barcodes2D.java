package hello.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Barcodes2D implements Serializable {
    private static final long serialVersionUID=31L;

    @JacksonXmlProperty(localName="Barcode2D")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Barcode2D> barcodes2D=new ArrayList<Barcode2D>();

    public List<Barcode2D> getBarcodes2D() {
        return barcodes2D;
    }

    public void setBarcodes2D(List<Barcode2D> barcodes2D) {
        this.barcodes2D = barcodes2D;
    }
}
