package com.fin.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Controller
public class HelloController {
    @GetMapping("/html1")
    public String html1() {
        return "/index1.html";
    }
    @GetMapping("/html2")
    public String html2() {
        return "/index2.html";
    }
}
