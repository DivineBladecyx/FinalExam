package com.fin.test.controller;



import com.fin.test.dimin.Entity.tcity;
import com.fin.test.dimin.Repository.tcityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class tcityController {
@Autowired
    private tcityRepository cityRepository;
@GetMapping(value="/city")
    public List<tcity> tcities(){
    return cityRepository.findAll();
}

}
