package com.fin.test.dimin.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Date;

@Entity
public class Messages {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String message_id;
    private String message_fromuser_id;
    private String message_touser_id;
    private Date message_time;
    private String message_type;
    private String message_infor;

    public String getMessage_id() {
        return message_id;
    }

    public void setMessage_id(String message_id) {
        this.message_id = message_id;
    }

    public String getMessage_fromuser_id() {
        return message_fromuser_id;
    }

    public void setMessage_fromuser_id(String message_fromuser_id) {
        this.message_fromuser_id = message_fromuser_id;
    }

    public String getMessage_touser_id() {
        return message_touser_id;
    }

    public void setMessage_touser_id(String message_touser_id) {
        this.message_touser_id = message_touser_id;
    }

    public Date getMessage_time() {
        return message_time;
    }

    public void setMessage_time(Date message_time) {
        this.message_time = message_time;
    }

    public String getMessage_type() {
        return message_type;
    }

    public void setMessage_type(String message_type) {
        this.message_type = message_type;
    }

    public String getMessage_infor() {
        return message_infor;
    }

    public void setMessage_infor(String message_infor) {
        this.message_infor = message_infor;
    }
}
