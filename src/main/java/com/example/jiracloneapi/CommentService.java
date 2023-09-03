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
public class CommentService {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    MongoTemplate mongoTemplate;

    public Comment updateComment(Comment comment,String taskId) {
        Optional<Comment> opComment = commentRepository.findCommentByCommentId(comment.getCommentId());
        if(opComment.isPresent()) {
            System.out.println("Inside comment is present");
            Comment comment1 = opComment.get();
            comment.setId(comment1.getId());
            comment.setUser(comment1.getUser());
            Query query = new Query(Criteria.where("commentId").is(comment.getCommentId()));
            FindAndReplaceOptions options = new FindAndReplaceOptions().returnNew();
            return mongoTemplate.findAndReplace(query,comment,options);
        }
        Comment comment1 = mongoTemplate.insert(comment,"comments");
        Query query = new Query(Criteria.where("taskId").is(taskId));
        Update update = new Update().push("comments",comment1.getId());
        mongoTemplate.findAndModify(query,update,Task.class);
        return comment1;
    }

    public void deleteComment(String commentId,String taskId) {
        Optional<Comment> opComment = commentRepository.findCommentByCommentId(commentId);
        Comment comment = null;
        if(opComment.isPresent())
            comment = opComment.get();
        else
            return;
        Query query = new Query(Criteria.where("taskId").is(taskId));
        Update update = new Update().pull("comments",comment.getId());
        mongoTemplate.findAndModify(query,update,Task.class);
        query = new Query(Criteria.where("commentId").is(commentId));
        mongoTemplate.findAndRemove(query,Comment.class);
    }
}
