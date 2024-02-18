package com.gw.camunda.core.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gw.camunda.common.utils.DateTimeUtils;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

/**
 * @author guanwu
 * @created on 2024-02-04 16:11:55
 **/

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "job_data_input")
public class JobDataInput {

    private String jobId;

    private List<Integer> value;

    private Long dataInputId;

    @JsonFormat(pattern = DateTimeUtils.DEFAULT_DATE_TIME_FORMAT, timezone = DateTimeUtils.DEFAULT_TIME_ZONE)
    private Date createTime;

    @JsonFormat(pattern = DateTimeUtils.DEFAULT_DATE_TIME_FORMAT, timezone = DateTimeUtils.DEFAULT_TIME_ZONE)
    private Date updateTime;
}
