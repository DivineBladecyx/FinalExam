package com.fin.test.service;

import com.fin.test.dimin.Entity.User;
import com.fin.test.dimin.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    public List<User> findALL(){
        return userRepository.findAll();
    }

}
