package hello.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.Objects;

@JacksonXmlRootElement(localName="Result")
public class Result {
    private static final long serialVersionId=23L;
    @JacksonXmlProperty
    private String result;

    public Result(){

    }

    public Result(String result){
        this.result=result;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Result)) return false;
        Result result1 = (Result) o;
        return Objects.equals(getResult(), result1.getResult());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getResult());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Result{");
        sb.append("result='").append(result).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
