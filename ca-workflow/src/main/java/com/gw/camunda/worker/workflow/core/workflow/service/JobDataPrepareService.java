package com.gw.camunda.worker.workflow.core.workflow.service;

import com.gw.camunda.common.CommonConstants;
import com.gw.camunda.common.utils.SnowflakeIdWorker;
import com.gw.camunda.core.entity.Job;
import com.gw.camunda.core.entity.JobDataInput;
import com.gw.camunda.core.service.JobDataInputService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.client.task.ExternalTask;
import org.camunda.bpm.client.variable.ClientValues;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author guanwu
 * @created on 2024-02-04 16:03:49
 **/

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class JobDataPrepareService {

    private final SnowflakeIdWorker snowflakeIdWorker;

    private final JobDataInputService jobDataInputService;


    public VariableMap handleDataPrepare(Job job, ExternalTask externalTask) {
        final Integer numberOfData = job.getNumberOfData();

        List<List<Integer>> partitionList = randomGenerateData(numberOfData);

        final List<Long> dataIdList = saveDataInput(partitionList, job);

        final VariableMap variableMap = createVariableMap(partitionList, dataIdList);

        variableMap.putAll(externalTask.getAllVariables());
        return variableMap;
    }

    private List<Long> saveDataInput(List<List<Integer>> partitionList, Job job) {
        List<Long> dataInputIdList = new ArrayList<>();
        List<JobDataInput> inputList = new ArrayList<>();
        for (List<Integer> integers : partitionList) {
             long inputId = snowflakeIdWorker.nextId();
             dataInputIdList.add(inputId);
            final JobDataInput build = JobDataInput.builder()
                    .dataInputId(inputId)
                    .jobId(job.getJobId())
                    .createTime(new Date())
                    .updateTime(new Date())
                    .value(integers)
                    .build();
            inputList.add(build);
        }
        jobDataInputService.saveDataInput(inputList);
        return dataInputIdList;
    }

    private static List<List<Integer>> randomGenerateData(int numberOfData) {

        final List<Integer> generateList = randomInt(numberOfData);

        List<List<Integer>> partitionList = new ArrayList<>();

        int psize = generateList.size() / 100;

        int mod = generateList.size() % 100;

        if (mod > 0) {
            psize ++;
        }

        int lastIndex;
        int endIndex;
        for (int i = 0; i < psize; i++) {
            lastIndex = i * 100;
            endIndex = lastIndex + 100;
            endIndex = Math.min(endIndex, generateList.size());
            List<Integer> itemList = generateList.subList(lastIndex, endIndex);
            partitionList.add(itemList);
        }

        return partitionList;
    }

    private static VariableMap createVariableMap(List<List<Integer>> partitionList,
                                                 List<Long> dataIdList) {
        VariableMap variableMap = Variables.createVariables();

        List<String> keyList = new ArrayList<>();
        for (int i = 0; i < partitionList.size(); i++) {
            String partitionKey = "instanceIndex_" + i;
            keyList.add(partitionKey);
//            List<Integer> itemList = new ArrayList<>(partitionList.get(i));
//            ObjectValue partitionValue = ClientValues
//                    .objectValue(itemList)
//                    .serializationDataFormat(Variables.SerializationDataFormats.JSON)
//                    .create();
            variableMap.putValueTyped(partitionKey, ClientValues.longValue(dataIdList.get(i)));
        }

        ObjectValue keyListValue = ClientValues
                .objectValue(keyList)
                .serializationDataFormat(Variables.SerializationDataFormats.JSON)
                .create();
        variableMap.putValueTyped(CommonConstants.INSTANCE_LIST, keyListValue);
        variableMap.putValueTyped(CommonConstants.INSTANCE_LIST_SIZE,
                ClientValues.integerValue(partitionList.size()));
        return variableMap;
    }

    private static List<Integer> randomInt(int size) {
        Random random = new Random();
        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            res.add(random.nextInt(100));
        }

        return res;
    }

    public static void main(String[] args) {
        final List<List<Integer>> lists = randomGenerateData(300);
        for (List<Integer> list : lists) {
            System.out.println(list.toString());
        }
    }
}
