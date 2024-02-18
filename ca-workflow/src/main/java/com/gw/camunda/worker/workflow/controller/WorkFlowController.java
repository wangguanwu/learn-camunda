package com.gw.camunda.worker.workflow.controller;

import com.gw.camunda.core.vo.JobRequestVO;
import com.gw.camunda.core.vo.JobResponseVO;
import com.gw.camunda.worker.workflow.service.WorkFlowService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author guanwu
 * @created on 2024-02-04 14:35:48
 **/

@RequestMapping("/workflow")
@RestController
@Slf4j
@RequiredArgsConstructor
public class WorkFlowController {

    private final WorkFlowService workFlowService;

    @GetMapping("/submit-job")
    public JobResponseVO submitJob(@RequestBody JobRequestVO jobRequestVO) {
        if (jobRequestVO.getJobName() == null) {
            throw new IllegalArgumentException();
        }

        if (jobRequestVO.getNumberOfData() == null) {
            throw new IllegalArgumentException();
        }
        return workFlowService.submitJob(jobRequestVO);
    }

}
