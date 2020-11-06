package hello.controllers;

import hello.model.Result;
import hello.model.User;
import hello.model.Users;
import hello.servises.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    IUserService userService;

    @GetMapping(value="/users",produces= MediaType.APPLICATION_XML_VALUE)
    public @ResponseBody Users showUsers(Model model) {
        return userService.findAll();
    }

    @RequestMapping(value = "/users/add", method = RequestMethod.POST,produces=MediaType.APPLICATION_XML_VALUE)
    public @ResponseBody Result addUser(@RequestParam("user_code") String userCode,
                          @RequestParam("user_login") String userLogin,
                          @RequestParam("user_password") String userPassword, Model model) throws IOException {
        User user = new User(userCode, userLogin, userPassword);
        userService.insert(user);
        return new Result("Add User");
    }

    @RequestMapping(value="/users/{user_code}",method=RequestMethod.PUT,produces=MediaType.APPLICATION_XML_VALUE)
    public @ResponseBody Result updateUser(@PathVariable("user_code") String userCode,
                      @RequestParam("user_login") String userLogin,
                      @RequestParam("user_password") String userPassword, Model model) throws Exception {
        userService.updateUser(userCode,userLogin,userPassword);
        return new Result("User update");
    }

    @RequestMapping(value = "/users/{user_code}", method = RequestMethod.DELETE,produces=MediaType.APPLICATION_XML_VALUE)
    public @ResponseBody Result deleteUser(@PathVariable("user_code") String userCode, Model model) throws IOException {
        userService.deleteById(userCode);
        return new Result("UserDelete");
    }

    @GetMapping(value="/users/{user_code}",produces=MediaType.APPLICATION_XML_VALUE)
    public @ResponseBody User showUserById(@PathVariable("user_code") String userCode) throws IOException {
        return userService.getUserById(userCode);
    }
}

