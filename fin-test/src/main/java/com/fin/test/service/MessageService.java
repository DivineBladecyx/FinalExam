package com.fin.test.service;

import com.fin.test.dimin.Entity.Messages;
import com.fin.test.dimin.Entity.User;
import com.fin.test.dimin.Repository.MessagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class MessageService {
    @Autowired
    private MessagesRepository messagesRepository;

    public List<Messages> findALL(){
        return  messagesRepository.findAll();
    }
    public void saveMessages(Messages messages) {
        messagesRepository.save(messages);
    }
}
