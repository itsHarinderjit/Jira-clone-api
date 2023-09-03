package com.example.jiracloneapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class TaskController {

    @Autowired
    private TaskService taskService;

    @MessageMapping("/user/updateTask/{projectId}")
    @SendTo("/topic/project/{projectId}")
    public ResponseEntity<Task> updateTask(@RequestBody String payload) throws JsonProcessingException {
        HashMap<String, String> mp = new ObjectMapper().readValue(payload, HashMap.class);
        String projectId = mp.get("projectId");
        Task task = new ObjectMapper().readValue(mp.get("data"),Task.class);
        return new ResponseEntity<>(taskService.updateTask(task,projectId), HttpStatus.OK);
    }

    @MessageMapping("/user/deleteTask/{projectId}")
    @SendTo("/topic/project/{projectId}")
    public ResponseEntity<Map<String,String>> deleteTask(@RequestBody String payload) throws JsonProcessingException {
        HashMap<String, String> mp = new ObjectMapper().readValue(payload, HashMap.class);
        taskService.deleteTask(mp.get("taskId"),mp.get("projectId"));
        return new ResponseEntity<>(mp,HttpStatus.OK);
    }
}
