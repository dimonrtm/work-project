package hello.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Users implements Serializable {
    private static final long serialVersionUID=22L;

    @JacksonXmlProperty(localName="User")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<User> users=new ArrayList<User>();

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
