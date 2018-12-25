package com.fin.test.service.servlet;


import com.fin.test.dimin.Entity.Messages;
import com.fin.test.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.Session;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;

@WebServlet(urlPatterns = "/DownloadServlet")
public class DownloadServlet extends HttpServlet {
    @Autowired
    private MessageService messageService;
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Session se= (Session) request.getSession();

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String fromuserid = request.getParameter("fromid");
        String senduserid = request.getParameter("sendid");
        String filename=fromuserid+"chatDocumentTo"+senduserid+".txt";

        List<Messages>messagesList=messageService.findALL();
        File file=new File(filename);
        try{
            if(!file.exists()){
                file.createNewFile();
            }
            FileOutputStream out=new FileOutputStream(file);
            PrintStream p=new PrintStream(out);
            for(int i=0;i<messagesList.size();i++){
                if((messagesList.get(i).getMessage_fromuser_id().equals(fromuserid)&&messagesList.get(i).getMessage_touser_id().equals(senduserid))||
                        (messagesList.get(i).getMessage_fromuser_id().equals(senduserid)&&messagesList.get(i).getMessage_touser_id().equals(fromuserid))){
                    String Finmessage="时间:"+messagesList.get(i).getMessage_time()+"用户："+messagesList.get(i).getMessage_fromuser_id()+"说："+messagesList.get(i).getMessage_infor().split("[|]")[1];
                    p.println(Finmessage);
                }
            }
            p.close();
            out.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        response.setHeader("content-disposition","attachment;filename="+ URLEncoder.encode(filename,"UTF-8"));
        FileInputStream in=new FileInputStream(file);
        OutputStream outputStream=response.getOutputStream();
        byte buffer[]=new byte[1024];
        int len=0;
        while((len=in.read(buffer))>0){
            outputStream.write(buffer,0,len);
        }
        in.close();
        outputStream.close();
    }
}
