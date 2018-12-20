package com.fin.test.controller;

import com.fin.test.dimin.Entity.User;
import com.fin.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping(value= "USER")
public class UserController {
    @Autowired
    private UserService userService;
    @RequestMapping(value = "",method = RequestMethod.GET)
    public List<User> findAll(){
        return userService.findALL();
    }

}