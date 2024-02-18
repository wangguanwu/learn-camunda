package com.gw.camunda.core.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gw.camunda.common.utils.DateTimeUtils;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * @author guanwu
 * @created on 2024-02-04 14:08:39
 **/

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "job")
public class Job {

    private String jobId;

    private String jobName;

    private String jobDesc;

    private String status;

    private Integer numberOfData;

    @JsonFormat(pattern = DateTimeUtils.DEFAULT_DATE_TIME_FORMAT, timezone = DateTimeUtils.DEFAULT_TIME_ZONE)
    private Date createTime;

    @JsonFormat(pattern = DateTimeUtils.DEFAULT_DATE_TIME_FORMAT, timezone = DateTimeUtils.DEFAULT_TIME_ZONE)
    private Date updateTime;

    @JsonFormat(pattern = DateTimeUtils.DEFAULT_DATE_TIME_FORMAT, timezone = DateTimeUtils.DEFAULT_TIME_ZONE)
    private Date completedTime;

    private Integer progressPercentage;
}
