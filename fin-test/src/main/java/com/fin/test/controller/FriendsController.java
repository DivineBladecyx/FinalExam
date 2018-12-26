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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        friends1.setF_group_name("我的好友");
        friends1.setF_user_id(userid);
        Friends friends2=new Friends();
        friends2.setF_friend_id(userid);
        friends2.setF_group_id("0");
        friends2.setF_group_name("我的好友");
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
    Map<String,List<User>>usermap=new HashMap<>();
    List<User>groupfriendlist=new ArrayList<>();
    List<String>grouplist=new ArrayList<>();

    for(int i=0;i<userfriendList.size();i++){
       grouplist.add(userfriendList.get(i).getF_group_name());
    }
    for (int i = 0; i < grouplist.size()-1; i++) {//从第一个数开始，到最后一个数-1次循环
        for (int j = grouplist.size()-1; j >i;j--) {//从最后一个数开始到i+1
            if(grouplist.get(j).equals(grouplist.get(i))){
                grouplist.remove(j);//这里的remove里的参数j就是角标，通过角标移除数据
            }
        }
    }
//将好友列表加入hashmap
    for(int i=0;i<grouplist.size();i++){
        List<String>friendidlist=new ArrayList<>();
        List<User>groupfrienduserlist=new ArrayList<>();
        for(int j=0;j<userfriendList.size();j++){
            if(userfriendList.get(j).getF_group_name().equals(grouplist.get(i))){
                friendidlist.add(userfriendList.get(j).getF_friend_id());
            }
        }
      for(int k=0;k<friendidlist.size();k++){
          for(int m=0;m<friendList.size();m++){
              if(friendList.get(m).getUser_id().equals(friendidlist.get(k))){
                  groupfrienduserlist.add(friendList.get(m));
              }
          }
      }
        usermap.put(grouplist.get(i),groupfrienduserlist);;
    }
    model.addAttribute("usermap",usermap);
    model.addAttribute("user_id",user.getUser_id());
   // model.addAttribute("friendList",userfriendList);
   // model.addAttribute("userfriendList",friendList);
    model.addAttribute("crowdsList",crowdsList);
    return "onchat";
}
}
