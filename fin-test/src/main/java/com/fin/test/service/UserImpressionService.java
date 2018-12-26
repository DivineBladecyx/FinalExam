package com.fin.test.service;

import com.fin.test.dimin.Entity.UserImpression;
import com.fin.test.dimin.Repository.UserImpressionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserImpressionService  {
    @Autowired
    private UserImpressionRepository userImpressionRepository;

    public List<UserImpression> findALL(){
        return  userImpressionRepository.findAll();
    }
    public void saveMessages(UserImpression userImpression) {
        userImpressionRepository.save(userImpression);
    }
}
