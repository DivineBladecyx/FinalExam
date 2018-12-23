package com.fin.test.service;

import com.fin.test.dimin.Entity.Crowds;
import com.fin.test.dimin.Repository.CrowdsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CrowdsService {
    @Autowired
    private CrowdsRepository crowdsRepository;
    public List<Crowds> findAll(){
        return crowdsRepository.findAll();
    }

}
