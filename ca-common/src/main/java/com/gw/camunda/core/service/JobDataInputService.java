package com.gw.camunda.core.service;

import com.gw.camunda.core.entity.JobDataInput;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * @author guanwu
 * @created on 2024-02-05 11:33:29
 **/

@Service
@Slf4j
@RequiredArgsConstructor
public class JobDataInputService {


    private final MongoTemplate mongoTemplate;

    public Collection<JobDataInput> saveDataInput(List<JobDataInput> jobDataInputs) {
        return mongoTemplate.insert(jobDataInputs, JobDataInput.class);
    }

    public JobDataInput findDataInputByJobIdAndDataInputId(String jobId, long inputId) {
        return mongoTemplate.findOne(Query.query(Criteria.where("jobId").is(jobId)
                .and("dataInputId").is(inputId)), JobDataInput.class);
    }
}
