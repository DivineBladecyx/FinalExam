package com.fin.test.controller;

import com.fin.test.dimin.Entity.User;
import com.fin.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value= "UserController")
public class UserController {
    @Autowired
    private UserService userService;
    @RequestMapping("/login")
    public String login(User user, Model model) {
        User user1=userService.findById(user.getUser_id());
        if(user1!=null) {
            if (user.getUser_password().equals(user1.getUser_password())) {
                model.addAttribute("user_id", user.getUser_id());//将user存放到session
                return "redirect:/FriendsController/showfriends?user_id="+user.getUser_id();
            } else {
                System.out.println("用户" + user.getUser_id() + "用户名或密码错误");
                return "Untitled-2";
            }
        }
        else{
            System.out.println("用户" + user.getUser_id() + "不存在");
            return "Login";
        }
    }
    @RequestMapping("/register")
    public String register(User user){
       List userList=userService.findALL();
       boolean isExist=false;
       for(int i=0;i<userList.size();i++){
           User user1=(User)userList.get(i);
           if(user.getUser_id().equals(user1.getUser_id())){
           isExist=true;
           break;
           }
       }
        if(isExist==false) {
            userService.registerUser(user);
            System.out.println("用户" + user.getUser_id() + "已注册");
            return "Untitled-2";
        }
        else{
            System.out.println("用户" + user.getUser_id() + "已存在");
            return "Untitled-1";
        }
    }

    @RequestMapping("/UserInfor")
    public String UserInfor(User user, Model model) {
        User user2=userService.findById(user.getUser_id());
        model.addAttribute("user",user2);
        return "/user_infor";
    }
    @RequestMapping(value = "EditInformation" ,method = RequestMethod.POST)
    @ResponseBody
    public String EditInformation(@RequestParam(value = "user_id",defaultValue = "")String user_id,@RequestParam(value = "user_name",defaultValue = "")String user_name,
                                  @RequestParam(value = "user_sex",defaultValue = "")String user_sex,@RequestParam(value = "user_age",defaultValue = "")String user_age,
                                  @RequestParam(value = "user_comments",defaultValue = "")String user_comments) {
       List<User>userList=userService.findALL();
       for(int i=0;i< userList.size();i++){
           if(userList.get(i).getUser_id().equals(user_id)){
               userList.get(i).setUser_id(user_id);
               userList.get(i).setUser_age(Integer.valueOf(user_age));
               userList.get(i).setUser_nickname(user_name);
               userList.get(i).setUser_comments(user_comments);
               userList.get(i).setUser_sex(Integer.valueOf(user_sex));

           userService.registerUser(userList.get(i));
           break;
           }
       }
        return "/showfriends";
    }

}
