package com.fin.test.WebSocketCore;

import com.fin.test.controller.MessagesController;
import com.fin.test.dimin.Entity.Crowds;
import com.fin.test.dimin.Entity.Friends;
import com.fin.test.dimin.Entity.Messages;
import com.fin.test.dimin.Entity.User;
import com.fin.test.dimin.Repository.MessagesRepository;
import com.fin.test.service.CrowdsService;
import com.fin.test.service.FriendsService;
import com.fin.test.service.MessageService;
import com.fin.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Component
@ServerEndpoint(value="/WebSocket/{id}")
public class WebSocketServer  {
    private static int onlineCount = 0;
    private static ConcurrentHashMap<String, WebSocketServer> webSocketSet = new ConcurrentHashMap<>();
    private Session session;
    private static Logger log = LogManager.getLogger(WebSocketServer.class);
    private static ApplicationContext applicationContext=null;
    private String id = "";
    public static void setApplicationContext(ApplicationContext Context){
        applicationContext=Context;
    }
    @Autowired
    private UserService userService;
    @Autowired
    private CrowdsService crowdsService;
    @OnOpen
    public void onOpen(@PathParam(value = "id") String id, Session session) {
        this.session = session;
        this.id = id;

        webSocketSet.put(id, this);
        addOnlineCount();
        log.info("用户" + id + "加入！当前在线人数为" + getOnlineCount());
        try {
            sendMessage("连接成功");
        } catch (IOException e) {
            log.error("websocket IO异常");
        }
    }

    @OnClose
    public void onClose() {
        webSocketSet.remove(this);  //从set中删除
        subOnlineCount();           //在线数减1
        log.info("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("来自客户端的消息:" + message);
        String[] Message = message.split("[|]");

        // System.out.println(Message[1]);
        if (Message.length == 4 && Integer.valueOf(Message[0]) < 100000) {//单聊天信息
            String tag = Message[2];
            switch (tag) {
                case "100001": {//1.单对单
                    String sendmessage = message;
                    String sendid = Message[3];
                    try {
                        sendtoUser(sendmessage, sendid);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
                case "100002": {//2.群聊

                }
                break;

                case "100009": {//9.视频消息

                }
                break;
            }
        } else if (Message.length == 3 && Integer.valueOf(Message[0]) < 100000) {//操作信息
            String tag = Message[1];
            switch (tag) {
                case "100003": {//3.好友添加
                    String sendid = Message[2];
                    try {

                        sendOperateMessage(message, sendid);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
                case "100004": {//4.好友添加成功
                    String sendid = Message[0];
                    try {

                        sendOperateMessage(message, sendid);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
                case "100008": {//8.拒绝添加
                    String sendid = Message[0];
                    try {

                        sendOperateMessage(message, sendid);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
                case "100009": {//查询好友信息

                    userService = applicationContext.getBean(UserService.class);
                    String sendid = Message[0];
                    String findid = Message[2];

                    List<User> userList = userService.findALL();
                    User friend = new User();
                    for (int i = 0; i < userList.size(); i++) {
                        if (findid.equals(userList.get(i).getUser_id())) {
                            friend = userList.get(i);
                            break;
                        }
                    }
                    String friendmessage = sendid + "|" + "100010" + "|" + friend.getUser_id() + "|" + friend.getUser_nickname() + "|" + friend.getUser_age() +
                            "|" + friend.getUser_sex() + "|" + friend.getUser_registime() + "|" + friend.getUser_comments();
                    try {
                        sendOperateMessage(friendmessage, sendid);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
                case "100006": {//6.群添加

                }
                break;
                case "100007": {//7.加群成功

                }
                break;
            }
        } else if (Integer.valueOf(Message[0]) > 100000) {//群聊和群操作
            String tag = Message[0];
            switch (tag) {
                case "100011": {//查询群成员信息
                    crowdsService = applicationContext.getBean(CrowdsService.class);
                    String crowdid=Message[2];
                    String sendid = Message[1];
                    String Finmessage="100012"+"|"+sendid+"|"+crowdid;
                    List<Crowds> crowdsList = crowdsService.findAll();
                    for (int i = 0; i < crowdsList.size(); i++) {
                        if (crowdid.equals(crowdsList.get(i).getCrowd_id())) {
                            Finmessage=Finmessage+"|"+crowdsList.get(i).getCrowd_owner_id();
                            break;
                        }
                    }
                    for (int i = 0; i < crowdsList.size(); i++) {
                        if (crowdid.equals(crowdsList.get(i).getCrowd_id())) {
                            Finmessage=Finmessage+"|"+crowdsList.get(i).getCrowd_member();
                        }
                    }
                    try {

                        sendOperateMessage(Finmessage, sendid);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
                case "100012": {//查询到的群成员信息
                    String sendid = Message[2];
                    try {

                        sendOperateMessage(message, sendid);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
                case "100002": {//群聊
                    String crowdid = Message[3];
                    List<Crowds> crowdsList = crowdsService.findAll();
                    for (int i = 0; i < crowdsList.size(); i++) {
                        if (crowdid.equals(crowdsList.get(i).getCrowd_id())) {
                            try {
                                sendtoCrowd(message,crowdsList.get(i).getCrowd_member());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
                break;
                case "100015": {//查询群资料
                    crowdsService = applicationContext.getBean(CrowdsService.class);
                    String crowdid=Message[2];
                    String sendid = Message[1];
                    String Finmessage="100016"+"|"+sendid+"|"+crowdid;
                    List<Crowds> crowdsList = crowdsService.findAll();
                    for (int i = 0; i < crowdsList.size(); i++) {
                        if (crowdid.equals(crowdsList.get(i).getCrowd_id())) {
                            Finmessage=Finmessage+"|"+crowdsList.get(i).getCrowd_owner_id();
                            break;
                        }
                    }
                    for (int i = 0; i < crowdsList.size(); i++) {
                        if (crowdid.equals(crowdsList.get(i).getCrowd_id())) {
                            Finmessage=Finmessage+"|"+crowdsList.get(i).getCrowd_member();
                        }
                    }
                    try {

                        sendOperateMessage(Finmessage, sendid);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            }

        }
    }
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误");
        error.printStackTrace();
    }
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }
    @Autowired
    private MessageService messageService;
    @PostMapping("")
    public void sendOperateMessage(String message,String sendUserId) throws IOException {//添加好友

        if (webSocketSet.get(sendUserId) != null) {
            if(!id.equals(sendUserId)) {
                    webSocketSet.get(sendUserId).sendMessage(message);//添加好友的消息
            }
            else {
                webSocketSet.get(sendUserId).sendMessage(message);
            }
        } else {
            //如果用户不在线则返回不在线信息给自己
            sendtoUser("当前用户不在线",id);
        }
    }
    public void sendtoCrowd(String message,String sendUserId) throws IOException {//聊天信息
       // messageService=applicationContext.getBean(MessageService.class);
        if (webSocketSet.get(sendUserId) != null) {
            if(!id.equals(sendUserId)) {
               /* Date date = new Date(System.currentTimeMillis());
                messageService.saveMessages(amessage);*/
                webSocketSet.get(sendUserId).sendMessage(message);
            }
            else {
                webSocketSet.get(sendUserId).sendMessage(message);
            }
        } else {
            //如果用户不在线则返回不在线信息给自己
            sendtoUser("当前用户不在线",id);
        }
    }
    public void sendtoUser(String message,String sendUserId) throws IOException {//聊天信息
        messageService=applicationContext.getBean(MessageService.class);
        if (webSocketSet.get(sendUserId) != null) {
            if(!id.equals(sendUserId)) {
                Date date = new Date(System.currentTimeMillis());
                Messages amessage=new Messages();
                amessage.setMessage_fromuser_id(id);
                amessage.setMessage_touser_id(sendUserId);
               amessage.setMessage_time(date);
                amessage.setMessage_infor(message);
               amessage.setMessage_type("0");
                webSocketSet.get(sendUserId).sendMessage(message);
                messageService.saveMessages(amessage);
            }
            else {
                webSocketSet.get(sendUserId).sendMessage(message);
            }
        } else {
            //如果用户不在线则返回不在线信息给自己
            sendtoUser("当前用户不在线",id);
        }
    }
    public void sendtoAll(String message) throws IOException {
        for (String key : webSocketSet.keySet()) {
            try {
                webSocketSet.get(key).sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }

}

