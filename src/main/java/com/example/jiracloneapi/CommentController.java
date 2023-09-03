package com.example.jiracloneapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.Map;

@Controller

public class CommentController {

    @Autowired
    CommentService commentService;

    @MessageMapping("/user/updateComment/{projectId}")
    @SendTo("/topic/project/{projectId}")
    public ResponseEntity<Map<String,String>> updateComment(@RequestBody String payload) throws JsonProcessingException {
        HashMap<String, String> mp = new ObjectMapper().readValue(payload, HashMap.class);
//        Comment cmm = new ObjectMapper().readValue(mp.get("comment"),Comment.class);
        Comment comment = commentService.updateComment(new ObjectMapper().convertValue(mp.get("comment"),Comment.class), mp.get("taskId"));
        System.out.println(comment.toString());
        mp.put("comment",new ObjectMapper().writeValueAsString(comment));
        return new ResponseEntity<>(mp, HttpStatus.OK);
    }

    @MessageMapping("/user/deleteComment/{projectId}")
    @SendTo("/topic/project/{projectId}")
    public ResponseEntity<Map<String,String>> deleteComment(@RequestBody String payload) throws JsonProcessingException {
        HashMap<String,String> mp = new ObjectMapper().readValue(payload,HashMap.class);
        commentService.deleteComment(mp.get("commentId"),mp.get("taskId"));
        return new ResponseEntity<>(mp,HttpStatus.OK);
    }
}
