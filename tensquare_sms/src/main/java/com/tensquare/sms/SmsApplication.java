package com.tensquare.sms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import util.IdWorker;

@SpringBootApplication
public class SmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmsApplication.class,args);
    }

    /**
     * 初始化 IdWork
     */
    @Bean
    public IdWorker idWork(){
        return new IdWorker(1,1);
    }
}
