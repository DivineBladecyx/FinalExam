package com.fin.test.service;

import com.fin.test.dimin.Entity.Friends;
import com.fin.test.dimin.Repository.FriendsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendsService {
    @Autowired
    private FriendsRepository friendsRepository;
    public List<Friends> findALL(){
        return friendsRepository.findAll();
    }
    public void saveFriends(Friends friends) {
        friendsRepository.save(friends);
    }
}
