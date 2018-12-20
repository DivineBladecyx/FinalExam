package com.fin.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Controller


public class HtmlController {
    @GetMapping("/Start")
    public String Start() {
        return "/Start.html";
    }
    @GetMapping("/Login")
    public String Login() {
        return "/Login.html";
    }
    @GetMapping("/Register")
    public String Register() {
        return "/Register.html";
    }
}
