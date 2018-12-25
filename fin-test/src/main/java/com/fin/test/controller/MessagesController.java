package com.fin.test.controller;

import com.fin.test.dimin.Entity.Messages;

import com.fin.test.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
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

    @RequestMapping(value = "setInlineMessageOnline" ,method = RequestMethod.POST)
    @ResponseBody
    public String setInlineMessageOnline(@RequestParam(value = "fromAndto",defaultValue = "")String fromAndto) {
        String fromuserid=fromAndto.split("[|]")[0];
        String senduserid=fromAndto.split("[|]")[1];
        List<Messages>messagesList=messageService.findALL();

        for(int i=0;i<messagesList.size();i++){
            if(messagesList.get(i).getMessage_fromuser_id().equals(fromuserid)&&messagesList.get(i).getMessage_touser_id().equals(senduserid)){
                messagesList.get(i).setMessage_type("1");
                messageService.saveMessages(messagesList.get(i));
                break;
            }
        }
        return "/showfriends";
    }
}
