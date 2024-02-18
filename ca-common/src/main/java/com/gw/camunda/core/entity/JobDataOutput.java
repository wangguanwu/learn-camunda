package com.gw.camunda.core.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gw.camunda.common.utils.DateTimeUtils;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * @author guanwu
 * @created on 2024-02-04 16:30:07
 **/

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "job_data_output")
public class JobDataOutput {

    private String jobId;

    private Double value;

    private Long dataId;

    @JsonFormat(pattern = DateTimeUtils.DEFAULT_DATE_TIME_FORMAT, timezone = DateTimeUtils.DEFAULT_TIME_ZONE)
    private Date createTime;

    @JsonFormat(pattern = DateTimeUtils.DEFAULT_DATE_TIME_FORMAT, timezone = DateTimeUtils.DEFAULT_TIME_ZONE)
    private Date updateTime;
}
