package com.gw.camunda.worker.workflow.core.workflow.handler;

import com.gw.camunda.common.CommonConstants;
import com.gw.camunda.core.entity.Job;
import com.gw.camunda.core.entity.JobStatusEnum;
import com.gw.camunda.core.service.CommonJobService;
import com.gw.camunda.worker.workflow.core.workflow.service.JobDataPrepareService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.client.spring.annotation.ExternalTaskSubscription;
import org.camunda.bpm.client.task.ExternalTask;
import org.camunda.bpm.client.task.ExternalTaskHandler;
import org.camunda.bpm.client.task.ExternalTaskService;
import org.camunda.bpm.engine.variable.VariableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author guanwu
 * @created on 2024-02-04 16:01:24
 **/

@Component("jobDataPrepareHandler")
@ExternalTaskSubscription("jobDataPrepareHandler")
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class JobDataPrepareHandler implements ExternalTaskHandler {

    private final JobDataPrepareService jobDataPrepareService;

    private final CommonJobService commonJobService;

    @Override
    public void execute(ExternalTask externalTask, ExternalTaskService externalTaskService) {


        log.info("fetched task from topic {}, executionId:{}, jobId:{}", externalTask.getTopicName(),
                externalTask.getExecutionId(), externalTask.getVariable(CommonConstants.JOB_ID));


        String jobId = externalTask.getVariable(CommonConstants.JOB_ID);

        commonJobService.updateJobStatus(jobId, JobStatusEnum.PROGRESSING.name());

        final Map<String, Object> allVariables = externalTask.getAllVariables();

        try {
            final Job job = commonJobService.findJobByJobId(jobId);
            if (job == null) {
                throw new RuntimeException("job not exist, jobId" + jobId);
            }
            final VariableMap variableMap = jobDataPrepareService.handleDataPrepare(job, externalTask);

            log.info("start to sleep to simulate...");
            TimeUnit.SECONDS.sleep(new Random().nextInt(10) + 5L);
            externalTaskService.complete(externalTask, variableMap);

        } catch (Exception ex) {
            log.error("====error of task from topic :[{}], executionId:[{}], msg:{}",  externalTask.getTopicName(),
                    externalTask.getExecutionId(), ex.getMessage());
            externalTaskService.handleBpmnError(externalTask, "500");
        }
        log.info("finished task from topic [{}], executionId:[{}]", externalTask.getTopicName(),
                externalTask.getExecutionId());


    }
}
