package com.fin.test.controller;

import com.fin.test.dimin.Entity.Crowds;
import com.fin.test.dimin.Entity.Friends;
import com.fin.test.dimin.Entity.User;
import com.fin.test.service.CrowdsService;
import com.fin.test.service.FriendsService;
import com.fin.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value= "FriendsController")
public class FriendsController {
@Autowired
    private FriendsService friendsService;
@Autowired
private UserService userService;
@Autowired
private CrowdsService crowdsService;
@RequestMapping("/showfriends")
    public String showfriends(User user, Model model){
    List<Friends>friendsList=friendsService.findALL();
    List<User>userList=userService.findALL();
    List<Friends>userfriendList=new ArrayList<Friends>();
    List<User>friendList=new ArrayList<User>();
    List<Crowds>allcrowdsList=crowdsService.findAll();
    List<Crowds>crowdsList=new ArrayList<Crowds>();
    List<User>crowdsMemberList=new ArrayList<User>();
    for(int i=0;i<allcrowdsList.size();i++){
        if(allcrowdsList.get(i).getCrowd_member().equals(user.getUser_id())||allcrowdsList.get(i).getCrowd_owner_id().equals(user.getUser_id())){
            crowdsList.add(allcrowdsList.get(i));
        }
    }
    for(int i=0;i<friendsList.size();i++){
        Friends friend=(Friends) friendsList.get(i);
        if(friend.getF_user_id().equals(user.getUser_id())){
        userfriendList.add(friend);
        }
    }
    for (int i=0;i<userfriendList.size();i++){
        for(int j=0;j<userList.size();j++) {
            if (userfriendList.get(i).getF_friend_id().equals(userList.get(j).getUser_id())) {
                friendList.add(userList.get(j));
            }
        }
    }
    model.addAttribute("user_id",user.getUser_id());
    model.addAttribute("userfriendList",friendList);
    model.addAttribute("crowdsList",crowdsList);
    List<String> crowdsIdList=new ArrayList<String>();
    for(int i=0;i<crowdsList.size();i++){
        crowdsIdList.add(crowdsList.get(i).getCrowd_id());
    }
    model.addAttribute("crowdsIdList",crowdsIdList);

    return "chat";
}

}
