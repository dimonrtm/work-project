package hello.servises;

import hello.model.User;
import hello.model.Users;

import java.util.List;

public interface IUserService {
    Users findAll();
    void insert(User user);
    void deleteById(String id);
    User getUserById(String id);
    void updateUser(String userCode,String userLogin,String userPassword) throws Exception;
}
