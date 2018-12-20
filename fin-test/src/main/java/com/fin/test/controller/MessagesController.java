package com.fin.test.controller;

import com.fin.test.dimin.Entity.Messages;
import com.fin.test.dimin.Entity.User;
import com.fin.test.service.MessageService;
import com.fin.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping(value= "MessagesController")
public class MessagesController {
    @Autowired
    private MessageService messageService;
    @RequestMapping(value = "",method = RequestMethod.GET)
    public List<Messages> findAll(){
        return messageService.findALL();
    }
    @PostMapping("")
    public void saveMessages(Messages messages){
        messageService.saveMessages(messages);
    }


}
