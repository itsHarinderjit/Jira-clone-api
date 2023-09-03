package com.example.jiracloneapi;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskRespository extends MongoRepository<Task, ObjectId> {

    Optional<Task> findTaskByTaskId(String taskId);
}
