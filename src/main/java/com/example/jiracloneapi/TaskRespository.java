package com.example.jiracloneapi;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskRespository extends MongoRepository<Task,String> {

    Optional<Task> findTaskByTaskId(String taskId);
}
