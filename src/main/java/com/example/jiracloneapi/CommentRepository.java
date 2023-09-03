package com.example.jiracloneapi;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentRepository extends MongoRepository<Comment,String> {
    Optional<Comment> findCommentByCommentId(String commentId);
}
