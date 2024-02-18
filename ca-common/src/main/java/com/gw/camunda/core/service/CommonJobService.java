package com.gw.camunda.core.service;

import com.gw.camunda.common.utils.JsonUtil;
import com.gw.camunda.core.entity.Job;
import com.mongodb.client.result.UpdateResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author guanwu
 * @created on 2024-02-04 14:49:25
 **/

@Service
@Slf4j
@RequiredArgsConstructor
public class CommonJobService {

    private final MongoTemplate mongoTemplate;


    public Job saveJob(Job job) {
        return mongoTemplate.insert(job);
    }

    public Job findJobByJobId(String jobId) {
        return mongoTemplate.findOne(Query.query(Criteria.where("jobId").is(jobId)), Job.class);
    }

    public void updateJobStatus(String jobId, String status) {
        Update update = new Update().set("updateTime", new Date())
                .set("status", status);

        UpdateResult result = mongoTemplate.updateFirst(Query.query(Criteria.where("jobId").is(jobId)),
                update, Job.class);

        log.info("update jobId :{}, status:{}, updateResult:{} ", jobId, status, JsonUtil.toJson(result));
    }

    public void updateJobProcessPercentage(String jobId, Integer percentage) {
        Update update = new Update().set("updateTime", new Date())
                .set("progressPercentage", percentage);

        UpdateResult result = mongoTemplate.updateFirst(Query.query(Criteria.where("jobId").is(jobId)),
                update, Job.class);

        log.info("update jobId :{}, progressPercentage:{}, updateResult:{} ", jobId, percentage, JsonUtil.toJson(result));
    }
}
