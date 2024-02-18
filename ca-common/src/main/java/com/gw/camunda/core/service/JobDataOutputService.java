package com.gw.camunda.core.service;

import com.gw.camunda.core.entity.JobDataInput;
import com.gw.camunda.core.entity.JobDataOutput;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author guanwu
 * @created on 2024-02-18 10:38:10
 **/

@Service
@Slf4j
@RequiredArgsConstructor
public class JobDataOutputService {

    private final MongoTemplate mongoTemplate;

    public JobDataOutput saveDataOutput(JobDataOutput jobDataOutput) {
        return mongoTemplate.save(jobDataOutput);
    }

    public List<JobDataOutput> findDataOutputByJob(String jobId) {
        return mongoTemplate.find(Query.query(Criteria.where("jobId").is(jobId)),
                JobDataOutput.class);
    }
}
