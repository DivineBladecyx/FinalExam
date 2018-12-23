package com.fin.test.WebSocketCore;

import com.fin.test.controller.MessagesController;
import com.fin.test.dimin.Entity.Messages;
import com.fin.test.dimin.Repository.MessagesRepository;
import com.fin.test.service.MessageService;
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
        String[] Message=message.split("[|]");

       // System.out.println(Message[1]);
        if(Message.length==4) {//聊天信息
            String tag=Message[2];
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
        }
        else if (Message.length==3) {//操作信息
            String tag=Message[1];
            switch (tag) {
                case "100003": {//3.好友添加
                    String sendid = Message[2];
                    try {

                        sendAdd(message, sendid);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
                case "100004": {//4.好友添加成功
                     String sendid=Message[0];
                    try {

                        sendAdd(message, sendid);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
                case "100008": {//8.图片消息
                    String sendid=Message[0];
                    try {

                        sendAdd(message, sendid);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
                case "100005": {//5.删除好友

                }
                break;
                case "100006": {//6.群添加

                }
                break;
                case "100007": {//7.加群成功

                }
                break;
            }
        }
        else{//群聊
            String tag=Message[2];
        }

      /*  else if(Message.length>2&&!message.split("[|]")[0].equals("")){//群聊
            String sendMessage=Message[0];
            for(int i=1;i<Message.length;i++){
                webSocketSet.put(Message[i],this);
            }
            try {
                sendtoAll(sendMessage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
      else if(Message.length==3&&!message.split("[|]")[0].equals("`")){//说明是添加好友成功的信息
            try {
                sendtoUseradd("您和"+message.split("[|]")[2]+"已加为好友,开始聊天吧！",message.split("[|]")[1]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(Message.length==3&&message.split("[|]")[0].equals("`")){
            try {
                sendtoUseradd(message.split("[|]")[2]+"已拒绝",message.split("[|]")[1]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(Message.length==1&&Integer.valueOf(message)<1000){//说明是添加好友的信息
            try {

                sendtoUseradd("用户"+"|"+id+"|"+"请求加您为好友",message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(Message.length==1&&Integer.valueOf(message)>1000){//说明是加群信息
        }
        else{//加群成功信息
        }*/
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
    public void sendAdd(String message,String sendUserId) throws IOException {//添加好友

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

