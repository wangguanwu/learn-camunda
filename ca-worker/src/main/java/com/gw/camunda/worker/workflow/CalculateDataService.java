package com.gw.camunda.worker.workflow;

import com.gw.camunda.core.entity.JobDataInput;
import com.gw.camunda.core.entity.JobDataOutput;
import com.gw.camunda.core.service.JobDataInputService;
import com.gw.camunda.core.service.JobDataOutputService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @author guanwu
 **/

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CalculateDataService {

    private final JobDataInputService jobDataInputService;

    private final JobDataOutputService  jobDataOutputService;

    public void processData(String jobId, long inputId) {

        log.info("process data for jobId: {}, inputId:{}", jobId, inputId);
        final JobDataInput dataInput = jobDataInputService.findDataInputByJobIdAndDataInputId(jobId,
                inputId);
        if (null == dataInput) {
            log.error("data input is empty for jobId:{}, inputId:{}", jobId, inputId);
            throw new RuntimeException("data input is empty");
        }
        final Optional<Integer> reduce = dataInput.getValue().stream().reduce(Integer::sum);

        reduce.map(e -> JobDataOutput.builder()
                .dataId(inputId)
                .jobId(jobId)
                .value(e.doubleValue())
                .createTime(new Date())
                .updateTime(new Date())
                .build())
                .ifPresent(jobDataOutputService::saveDataOutput);

        try {
            TimeUnit.SECONDS.sleep(3L);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }

        log.info("process data for jobId: {}, inputId:{}", jobId, inputId);
    }
}
