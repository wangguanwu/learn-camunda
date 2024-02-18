package com.gw.camunda.core.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gw.camunda.common.utils.DateTimeUtils;
import lombok.*;

import java.util.Date;

/**
 * @author guanwu
 * @created on 2024-02-04 16:54:04
 **/

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobResponseVO {

    private String jobId;

    private String jobName;

    private String jobDesc;

    private String status;

    private Integer numberOfData;

    @JsonFormat(pattern = DateTimeUtils.DEFAULT_DATE_TIME_FORMAT, timezone = DateTimeUtils.DEFAULT_TIME_ZONE)
    private Date createTime;

}
