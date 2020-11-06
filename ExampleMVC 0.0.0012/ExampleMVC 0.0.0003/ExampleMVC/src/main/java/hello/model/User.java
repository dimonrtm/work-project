package hello.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name="users")
@JacksonXmlRootElement(localName="User")
public class User implements Serializable {
    private static final long serialVersionUID=21L;

    @Id
    @JacksonXmlProperty
    private String userCode;
    @JacksonXmlProperty
    private String userLogin;
    @JacksonXmlProperty
    private String userPassword;

    public User(){

    }

    public User(String userCode, String userLogin, String userPassword){
        this.userCode=userCode;
        this.userLogin=userLogin;
        this.userPassword=userPassword;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(getUserCode(), user.getUserCode()) &&
                Objects.equals(getUserLogin(), user.getUserLogin()) &&
                Objects.equals(getUserPassword(), user.getUserPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserCode(), getUserLogin(), getUserPassword());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("userCode='").append(userCode).append('\'');
        sb.append(", userLogin='").append(userLogin).append('\'');
        sb.append(", userPassword='").append(userPassword).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
