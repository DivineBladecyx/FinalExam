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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
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


    @RequestMapping(value = "getfriend" ,method = RequestMethod.POST)
    @ResponseBody
    public User getfriend(@RequestParam(value = "friend",defaultValue = "")String friend,Model model) {
        List<User>userList=userService.findALL();
        User friend1=new User();
        for (int i=0;i<userList.size();i++){
            if(friend.equals(userList.get(i).getUser_id())){
                friend1=userList.get(i);
                break;
            }
        }
        return friend1;
    }
    @RequestMapping(value = "getStringParam" ,method = RequestMethod.POST)
    @ResponseBody
    public String getStringParam(@RequestParam(value = "fromAnduser",defaultValue = "")String fromAnduser) {
        System.out.println(fromAnduser);
        String fromuserid=fromAnduser.split("[|]")[0];
        String userid=fromAnduser.split("[|]")[1];
        Friends friends1=new Friends();
        friends1.setF_friend_id(fromuserid);
        friends1.setF_group_id("0");
        friends1.setF_user_id(userid);
        Friends friends2=new Friends();
        friends2.setF_friend_id(userid);
        friends2.setF_group_id("0");
        friends2.setF_user_id(fromuserid);
      friendsService.saveFriends(friends1);
      friendsService.saveFriends(friends2);
        return "/showfriends";
    }

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
            if (allcrowdsList.get(i).getCrowd_member().equals(user.getUser_id()) || allcrowdsList.get(i).getCrowd_owner_id().equals(user.getUser_id())) {
                crowdsList.add(allcrowdsList.get(i));
        }
    }
    for (int i = 0; i < crowdsList.size() - 1; i++) {
        for (int j = crowdsList.size() - 1; j > i; j--) {
            if (crowdsList.get(j).getCrowd_id().equals(crowdsList.get(i).getCrowd_id())) {
                crowdsList.remove(j);
            }
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
    return "chat";
}
}
