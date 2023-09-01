package com.example.jiracloneapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    @Autowired
    private TaskRespository taskRespository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public Task addTask(Task task,String projectId) {
        Query query = new Query(Criteria.where("projectId").is(projectId));
        Update update = new Update().push("tasks",task.getId());
        mongoTemplate.findAndModify(query,update,Project.class);
        return mongoTemplate.save(task,"tasks");
    }
}
