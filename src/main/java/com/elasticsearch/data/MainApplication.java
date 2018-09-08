package com.elasticsearch.data;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * MainApplication class
 *
 * @author Lasse
 * @date 2018/9/6
 */
@SpringBootApplication
@Component
@EnableTransactionManagement
@ComponentScan(basePackages = {"com.elasticsearch.data"})
@MapperScan(basePackages = {"com.elasticsearch.data.dao"})
public class MainApplication {
    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }
}
