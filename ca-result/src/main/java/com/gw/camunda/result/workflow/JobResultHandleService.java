package com.gw.camunda.result.workflow;

import com.gw.camunda.common.CommonConstants;
import com.gw.camunda.core.entity.JobDataOutput;
import com.gw.camunda.core.entity.JobResult;
import com.gw.camunda.core.service.JobDataOutputService;
import com.gw.camunda.core.service.JobResultService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.client.task.ExternalTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @author guanwu
 **/


@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class JobResultHandleService {

    private final JobDataOutputService jobDataOutputService;

    private final JobResultService jobResultService;

    public void handleJobResult(String jobId, ExternalTask externalTask) {

        final List<JobDataOutput> dataOutputByJob = jobDataOutputService.findDataOutputByJob(jobId);

        if (CollectionUtils.isEmpty(dataOutputByJob)) {
            throw new RuntimeException("output is empty " + jobId);
        }

        Integer partitionListSize = externalTask.getVariable(CommonConstants.INSTANCE_LIST_SIZE);

        if (null == partitionListSize) {
            throw new RuntimeException("partitionListSize is empty " + jobId);
        }

        log.info("partitionSize is {}, jobOutput size is :{}", partitionListSize, dataOutputByJob.size());

        if (partitionListSize > dataOutputByJob.size()) {
            throw new RuntimeException("calculate stage is not fully completed! " + jobId);
        }

        final Optional<Double> reduce = dataOutputByJob.stream()
                .map(JobDataOutput::getValue)
                .reduce(Double::sum);

        log.info("start to save job result ...");
        reduce.map(r -> JobResult.builder()
        .result(r)
        .jobId(jobId)
        .createTime(new Date())
        .updateTime(new Date())
        .build())
        .ifPresent(jobResultService::saveJobResult);

        try {
            TimeUnit.SECONDS.sleep(5L);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
    }
}
