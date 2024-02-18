package com.gw.camunda.core.service;

import com.gw.camunda.core.entity.JobResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;


/**
 * @author guanwu
 **/

@Service
@Slf4j
@RequiredArgsConstructor
public class JobResultService {

    private final MongoTemplate mongoTemplate;

    public JobResult saveJobResult(JobResult jobResult) {
        return mongoTemplate.save(jobResult);
    }

    public JobResult findJobResultByJobId(String jobId) {
        return mongoTemplate.findOne(Query.query(Criteria.where("jobId").is(jobId)),
                JobResult.class);
    }
}
