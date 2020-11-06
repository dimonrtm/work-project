package hello.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AdmissionProducts implements Serializable {
    private static final long serialVersionUID=35L;

    @JacksonXmlProperty(localName = "AdmissionProduct")
    @JacksonXmlElementWrapper(useWrapping=false)
    List<AdmissionProduct> admissionProducts=new ArrayList<AdmissionProduct>();

    public List<AdmissionProduct> getAdmissionProducts() {
        return admissionProducts;
    }

    public void setAdmissionProducts(List<AdmissionProduct> admissionProducts) {
        this.admissionProducts = admissionProducts;
    }
}
