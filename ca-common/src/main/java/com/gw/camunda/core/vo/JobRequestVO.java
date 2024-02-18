package com.gw.camunda.core.vo;

import lombok.*;

/**
 * @author guanwu
 * @created on 2024-02-04 16:48:17
 **/

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobRequestVO {
    private String jobName;

    private Integer numberOfData;

    private String jobDesc;
}
