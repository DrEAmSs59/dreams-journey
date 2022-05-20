package com.dreamss.dreamjourneyuser;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author DrEAmSs
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.dreamss.dreamjourneyuser.feign")
@MapperScan("com.dreamss.dreamjourneyuser.mapper")
public class DreamJourneyUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(DreamJourneyUserApplication.class, args);
    }

}
