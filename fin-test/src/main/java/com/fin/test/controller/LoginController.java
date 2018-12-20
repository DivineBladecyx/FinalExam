package com.fin.test.controller;

import com.fin.test.dimin.Entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
@Controller
@SessionAttributes("user_id")
public class LoginController {

    @GetMapping("/")
    public String login() {
        return "/Login.html";
    }

    @RequestMapping("/login")
    public String login(User user, Model model) {
            model.addAttribute("user_id", user.getUser_id());//将user存放到session
        return "chat";
    }

}
