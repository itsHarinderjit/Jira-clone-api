package com.example.jiracloneapi;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRespository extends MongoRepository<Task,String> {
}
