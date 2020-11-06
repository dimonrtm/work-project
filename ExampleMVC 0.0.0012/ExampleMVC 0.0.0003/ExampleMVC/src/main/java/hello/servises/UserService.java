package hello.servises;

import hello.model.User;
import hello.model.Users;
import hello.repository.AddUserRepository;
import hello.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {
    @Autowired
    private UserRepository repository;
    @Autowired
    AddUserRepository addRepository;

    @Override
    public Users findAll() {
        List<User> users = (List<User>) repository.findAll();
        Users serUsers=new Users();
        serUsers.setUsers(users);
        return serUsers;
    }

    @Override
    public void insert(User user) {
        addRepository.insertUser(user);
    }

    @Override
    public void deleteById(String id){
        repository.deleteById(id);
    }

    @Override
    public User getUserById(String id){
        User user=repository.findById(id).orElse(new User());
        return user;
    }
    @Override
    public void updateUser(String userCode,String userLogin,String userPassword) throws Exception {
        User user=repository.findById(userCode).orElseThrow(()->new Exception("User not Found in Data Base"));
        user.setUserLogin(userLogin);
        user.setUserPassword(userPassword);
        repository.save(user);
    }
}
