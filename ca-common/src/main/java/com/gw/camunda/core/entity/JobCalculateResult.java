package com.gw.camunda.core.entity;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * @author guanwu
 * @created on 2024-02-04 16:14:48
 **/

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "job_cal_result")
public class JobCalculateResult {

    private String jobId;

    private String result;

    private Date createTime;

    private Date updateTime;

}
