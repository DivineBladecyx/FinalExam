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
    @RequestMapping("/UserLogin")
    public String thymeleaf(Model model){
        model.addAttribute("name","yuanfei");
        return "Login";
    }

    @GetMapping("/html2")
    public String html2() {
        return "/index2.html";
    }
    @GetMapping("/servlet")
    public String servlet1() {
        return "/UserServlet";
    }

}
