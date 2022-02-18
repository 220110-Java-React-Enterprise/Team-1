package com.revature.springbootdemo.beans.controller;


import com.revature.springbootdemo.beans.models.UserModel;
import com.revature.springbootdemo.beans.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/register")
public class UserController {


    public static UserRepo userRepo;

    @Autowired
    public UserController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String Login(@RequestParam(value = "myFirstname", defaultValue = "userFirst") String FirstName,
                        @RequestParam(value = "myLastname", defaultValue = "userLast") String LastName,
                        @RequestParam(value = "myPassword", defaultValue = "1111") String Password,
                        @RequestParam(value = "myEmail", defaultValue = "user@gmail.com") String Email) {

        UserModel user = new UserModel(FirstName, LastName, Password, Email);
        userRepo.save(user);

        return String.format("have added the user  %s and %s and %s nad %s successfully", FirstName, LastName, Password, Email);


    }
}
