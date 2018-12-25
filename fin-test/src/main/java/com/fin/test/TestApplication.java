package com.fin.test;

import com.fin.test.WebSocketCore.WebSocketServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@ServletComponentScan
public class TestApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {

        ConfigurableApplicationContext context=SpringApplication.run(TestApplication.class, args);
        WebSocketServer.setApplicationContext(context);
    }

}

