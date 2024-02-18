package com.gw.camunda.result.controller;

import com.gw.camunda.core.entity.JobResult;
import com.gw.camunda.core.service.JobResultService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author guanwu
 * @created on 2024-02-18 10:45:57
 **/

@RestController
@RequestMapping("/result")
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class JobResultController {

    private final JobResultService jobResultService;

    @GetMapping("/jobResult")
    public JobResult getJobResult(@RequestParam("jobId") String jobId) {
        final JobResult jobResultByJobId = jobResultService.findJobResultByJobId(jobId);
        if (null == jobResultByJobId) {
            return new JobResult();
        }
        return jobResultByJobId;
    }
}
