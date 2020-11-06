package hello.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Admissions implements Serializable {
    private static final long serialVersionUID=39L;

    @JacksonXmlProperty(localName = "Admission")
    @JacksonXmlElementWrapper(useWrapping=false)
    List<Admision> admissions=new ArrayList<Admision>();

    public List<Admision> getAdmissions() {
        return admissions;
    }

    public void setAdmissions(List<Admision> admissions) {
        this.admissions = admissions;
    }
}
