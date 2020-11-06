package hello.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Balances implements Serializable {
    private static final long serialVersionUID=33L;

    @JacksonXmlProperty(localName="Balance")
    @JacksonXmlElementWrapper(useWrapping = false)
    List<Balance> balances=new ArrayList<Balance>();

    public List<Balance> getBalances() {
        return balances;
    }

    public void setBalances(List<Balance> balances) {
        this.balances = balances;
    }
}
