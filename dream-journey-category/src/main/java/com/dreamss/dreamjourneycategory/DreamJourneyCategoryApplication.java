package com.dreamss.dreamjourneycategory;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients("com.dreamss.dreamjourneycategory.feign")
@MapperScan("com.dreamss.dreamjourneycategory.mapper")
public class DreamJourneyCategoryApplication {

    public static void main(String[] args) {
        SpringApplication.run(DreamJourneyCategoryApplication.class, args);
    }

}
