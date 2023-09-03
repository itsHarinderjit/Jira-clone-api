package com.example.jiracloneapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndReplaceOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRespository taskRespository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public Task updateTask(Task task,String projectId) {
        Optional<Task> opTask = taskRespository.findTaskByTaskId(task.getTaskId());
        if(opTask.isPresent()) {
            Task task1 = opTask.get();
            task.setId(task1.getId());
            task.setComments(task1.getComments());
            Query query = new Query(Criteria.where("taskId").is(task.getTaskId()));
            FindAndReplaceOptions options = new FindAndReplaceOptions().returnNew();
            return mongoTemplate.findAndReplace(query,task,options);
        }
        Task task1 = mongoTemplate.insert(task,"tasks");
        Query query = new Query(Criteria.where("projectId").is(projectId));
        Update update = new Update().push("tasks",task1.getId());
        mongoTemplate.findAndModify(query,update,Project.class);
        return task1;
    }

//    public Task updateTask(Task task) {
//        System.out.println(task.getTaskId());
//        Optional<Task> opTask = taskRespository.findTaskByTaskId(task.getTaskId());
//        Task task1 = null;
//        System.out.println("Inside update task");
//        if(opTask.isPresent())
//            task1 = opTask.get();
//        else
//            return new Task();
//        task.setId(task1.getId());
//        task.setComments(task1.getComments());
//        System.out.println("Task id: " + task.getTaskId());
//        Query query = new Query(Criteria.where("taskId").is(task.getTaskId()));
//        FindAndReplaceOptions findAndReplaceOptions = new FindAndReplaceOptions().returnNew();
//        return mongoTemplate.findAndReplace(query,task,findAndReplaceOptions);
//    }

    public void deleteTask(String taskId,String projectId) {
        Query query = new Query(Criteria.where("taskId").is(taskId));
        Optional<Task> opTask = taskRespository.findTaskByTaskId(taskId);
        Task task = null;
        if(opTask.isPresent())
            task = opTask.get();
        else
            return;
        mongoTemplate.findAndRemove(query,Task.class);
        query = new Query(Criteria.where("projectId").is(projectId));
        Update update = new Update().pull("tasks",task.getId());
        mongoTemplate.findAndModify(query,update,Project.class);
    }
}
