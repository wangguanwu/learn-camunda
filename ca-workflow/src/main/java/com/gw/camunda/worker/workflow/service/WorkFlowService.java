package com.gw.camunda.worker.workflow.service;

import com.gw.camunda.common.CommonConstants;
import com.gw.camunda.common.utils.DateTimeUtils;
import com.gw.camunda.common.utils.SnowflakeIdWorker;
import com.gw.camunda.core.entity.Job;
import com.gw.camunda.core.entity.JobStatusEnum;
import com.gw.camunda.core.service.CommonJobService;
import com.gw.camunda.core.vo.JobRequestVO;
import com.gw.camunda.core.vo.JobResponseVO;
import com.gw.camunda.worker.workflow.common.WorkflowConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.Executor;

/**
 * @author guanwu
 * @created on 2024-02-04 16:00:34
 **/

@Service
@Slf4j
@RequiredArgsConstructor
public class WorkFlowService {

    private final CommonJobService commonJobService;

    private final SnowflakeIdWorker snowflakeIdWorker;

    private final Executor asyncExecutor;

    private final RuntimeService runtimeService;


    public JobResponseVO submitJob(JobRequestVO jobRequestVO) {
        Job job = Job.builder()
                .jobDesc(jobRequestVO.getJobDesc())
                .jobName(jobRequestVO.getJobName())
                .jobId(generateJobId())
                .createTime(new Date())
                .updateTime(new Date())
                .status(JobStatusEnum.CREATED.name())
                .numberOfData(jobRequestVO.getNumberOfData())
                .build();
        commonJobService.saveJob(job);
        submitJobToWorkFlow(job);
        return JobResponseVO.builder()
                .jobDesc(job.getJobDesc())
                .jobName(job.getJobName())
                .jobId(job.getJobId())
                .createTime(job.getCreateTime())
                .status(JobStatusEnum.SUBMITTED.name())
                .numberOfData(jobRequestVO.getNumberOfData())
                .build();
    }

    public void submitJobToWorkFlow(Job job) {

        try {
            VariableMap map = Variables.createVariables();

            map.put(CommonConstants.JOB_ID, job.getJobId());
            map.put(CommonConstants.JOB_DATA_SIZE, job.getNumberOfData());

            commonJobService.updateJobProcessPercentage(job.getJobId(), 0);

            asyncExecutor.execute(() -> startWorkflow(WorkflowConstants.JOB_PROCESS_ENGINE_KEY, map));

            commonJobService.updateJobStatus(job.getJobId(), JobStatusEnum.SUBMITTED.name());

        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new RuntimeException(ex);
        }
    }

    @Async
    public void startWorkflow(String processKey, Map<String, Object> variables) {
        log.info("Start workflow processKey :{}", processKey);
        ProcessInstance instance = runtimeService.createProcessInstanceByKey(processKey)
                .setVariables(variables)
                .execute();

        String jobId = variables.get(CommonConstants.JOB_ID).toString();

        String processInstanceId = instance.getProcessInstanceId();

        log.info("Start workflow processKey:{}, processInstanceId:{}, jobId:{}", processKey,
                processInstanceId, jobId);

    }

    private String generateJobId() {
        return String.format("%s_%s", DateTimeUtils.getDefaultDateFormatString(new Date()),
                snowflakeIdWorker.nextId());
    }

}
