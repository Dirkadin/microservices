package com.dirkadin.ordering;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@EnableEurekaClient
@SpringBootApplication
@EnableFeignClients(basePackages = "com.dirkadin.clients")
@PropertySources({@PropertySource("classpath:clients-${spring.profiles.active}.properties")})
public class OrderingApplication {

  public static void main(String[] args) {
    SpringApplication.run(OrderingApplication.class, args);
  }
}
