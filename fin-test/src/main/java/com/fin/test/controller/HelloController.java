package com.fin.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
@RestController
@EnableAutoConfiguration
public class HelloController {
    @RequestMapping(value = {"/t","/temp","/template"},method = RequestMethod.GET)
    public String showIndexHtml(){
        return "index";
    }

}
