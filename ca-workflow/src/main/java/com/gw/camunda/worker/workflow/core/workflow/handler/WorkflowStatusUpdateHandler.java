package com.gw.camunda.worker.workflow.core.workflow.handler;

import com.gw.camunda.common.CommonConstants;
import com.gw.camunda.core.entity.JobResult;
import com.gw.camunda.core.entity.JobStatusEnum;
import com.gw.camunda.core.service.CommonJobService;
import com.gw.camunda.core.service.JobResultService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author guanwu
 **/

@Component("workflowStatusUpdateHandler")
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class WorkflowStatusUpdateHandler implements JavaDelegate {

    private final CommonJobService commonJobService;

    private final JobResultService jobResultService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        log.info("start updating job {} status from workflow===>", delegateExecution.getVariable(CommonConstants.JOB_ID));

        String jobId = (String) delegateExecution.getVariable(CommonConstants.JOB_ID);
        final JobResult jobResultByJobId = jobResultService.findJobResultByJobId(jobId);

        if (jobResultByJobId == null) {
            commonJobService.updateJobStatus(jobId, JobStatusEnum.FAILED.name());
        }

        commonJobService.updateJobStatus(jobId, JobStatusEnum.COMPLETED.name());

        log.info("<===finished updating job {} status from workflow", delegateExecution.getVariable(CommonConstants.JOB_ID));

    }
}
