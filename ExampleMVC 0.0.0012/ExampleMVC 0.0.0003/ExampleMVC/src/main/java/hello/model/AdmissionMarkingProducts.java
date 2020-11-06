package hello.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AdmissionMarkingProducts implements Serializable {
    private static final long serialVersionUID=37L;

    @JacksonXmlProperty(localName="AdmissionMarkingProduct")
    @JacksonXmlElementWrapper(useWrapping=false)
    List<AdmissionMarkingProduct> admissionMarkingProducts=new ArrayList<AdmissionMarkingProduct>();

    public List<AdmissionMarkingProduct> getAdmissionMarkingProducts() {
        return admissionMarkingProducts;
    }

    public void setAdmissionMarkingProducts(List<AdmissionMarkingProduct> admissionMarkingProducts) {
        this.admissionMarkingProducts = admissionMarkingProducts;
    }
}
