package com.gw.camunda.worker.workflow;

import com.gw.camunda.common.CommonConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.client.spring.annotation.ExternalTaskSubscription;
import org.camunda.bpm.client.task.ExternalTask;
import org.camunda.bpm.client.task.ExternalTaskHandler;
import org.camunda.bpm.client.task.ExternalTaskService;
import org.camunda.bpm.engine.variable.value.LongValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author guanwu
 **/

@Component
@Slf4j
@ExternalTaskSubscription("calculateDataHandler")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CalculateDataHandler implements ExternalTaskHandler {

    private final CalculateDataService calculateDataService;


    @Override
    public void execute(ExternalTask externalTask, ExternalTaskService externalTaskService) {

        String instanceKey = externalTask.getVariable(CommonConstants.INSTANCE_KEY);
        log.info("fetched task from topic {}, executionId:{}, jobId:{}, instanceKey:{}", externalTask.getTopicName(),
                externalTask.getExecutionId(), externalTask.getVariable(CommonConstants.JOB_ID),
                instanceKey);

        String jobId = externalTask.getVariable(CommonConstants.JOB_ID);

        String partitionKey = externalTask.getVariable(CommonConstants.INSTANCE_KEY);

        LongValue variableTyped = externalTask.getVariableTyped(partitionKey);

        long inputId = variableTyped.getValue();

        calculateDataService.processData(jobId, inputId);

        externalTaskService.complete(externalTask, externalTask.getAllVariables());

        log.info("finished task from topic [{}], executionId:[{}], instanceKey:{}", externalTask.getTopicName(),
                externalTask.getExecutionId(), instanceKey);
    }
}
