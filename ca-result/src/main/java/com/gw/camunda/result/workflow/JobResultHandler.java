package com.gw.camunda.result.workflow;

import com.gw.camunda.common.CommonConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.client.spring.annotation.ExternalTaskSubscription;
import org.camunda.bpm.client.task.ExternalTask;
import org.camunda.bpm.client.task.ExternalTaskHandler;
import org.camunda.bpm.client.task.ExternalTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author guanwu
 **/

@Component
@Slf4j
@ExternalTaskSubscription("jobResultHandler")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class JobResultHandler implements ExternalTaskHandler {

    private final JobResultHandleService jobResultService;

    @Override
    public void execute(ExternalTask externalTask, ExternalTaskService externalTaskService) {

        log.info("fetched task from topic {}, executionId:{}, jobId:{}", externalTask.getTopicName(),
                externalTask.getExecutionId(), externalTask.getVariable(CommonConstants.JOB_ID));

        String jobId = externalTask.getVariable(CommonConstants.JOB_ID);
        jobResultService.handleJobResult(jobId, externalTask);
        externalTaskService.complete(externalTask, externalTask.getAllVariables());
        log.info("finished task from topic [{}], executionId:[{}]", externalTask.getTopicName(),
                externalTask.getExecutionId());

    }
}
