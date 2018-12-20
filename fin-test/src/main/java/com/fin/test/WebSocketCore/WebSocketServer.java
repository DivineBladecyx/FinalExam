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
        //可以自己约定字符串内容，比如 内容|0 表示信息群发，内容|X 表示信息发给id为X的用户
        String sendMessage = message.split("[|]")[0];
        String sendUserId = message.split("[|]")[1];

        try {
            if (sendUserId.equals("0"))
                sendtoAll(sendMessage);
            else
                sendtoUser(sendMessage, sendUserId);
        } catch (IOException e) {
            e.printStackTrace();
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
    public void sendtoUser(String message,String sendUserId) throws IOException {
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
                webSocketSet.get(sendUserId).sendMessage("用户" + id + "发来消息：" + " <br/> " + message);
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

