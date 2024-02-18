package com.gw.camunda.common.utils.config;

import com.gw.camunda.common.utils.SnowflakeIdWorker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author guanwu
 * @created on 2024-02-04 16:25:32
 **/

@Configuration
public class CommonConfig {

    @Value("${ca.snowflake.worker-id}")
    private int workerId = 0;

    @Bean
    public SnowflakeIdWorker snowflakeIdWorker() {
        return new SnowflakeIdWorker(this.workerId, 0);
    }

}
