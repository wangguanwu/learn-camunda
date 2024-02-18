package com.gw.camunda.worker.workflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author guanwu
 * @created on 2024-02-01 17:16:03
 **/

@EnableAsync
@EnableMongoRepositories(basePackages = {"com.gw.camunda"})
@ComponentScan(basePackages = {"com.gw.camunda"})
@SpringBootApplication
public class CaWorkflowApplication {

    public static void main(String[] args) {
        SpringApplication.run(CaWorkflowApplication.class, args);
    }
}
