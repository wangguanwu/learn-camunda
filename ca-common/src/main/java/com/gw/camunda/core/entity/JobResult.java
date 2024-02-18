package com.gw.camunda.core.entity;

/**
 * @author guanwu
 * @created on 2024-02-18 10:31:11
 **/

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gw.camunda.common.utils.DateTimeUtils;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "job_result")
public class JobResult {

    private String jobId;

    private Double result;

    @JsonFormat(pattern = DateTimeUtils.DEFAULT_DATE_TIME_FORMAT, timezone = DateTimeUtils.DEFAULT_TIME_ZONE)
    private Date createTime;

    @JsonFormat(pattern = DateTimeUtils.DEFAULT_DATE_TIME_FORMAT, timezone = DateTimeUtils.DEFAULT_TIME_ZONE)
    private Date updateTime;
}
