package com.example.jiracloneapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class TaskController {

    @Autowired
    private TaskService taskService;

    @MessageMapping("/user/addTask/{projectId}")
    @SendTo("/topic/project/{projectId}")
    public ResponseEntity<Task> addTask(@RequestParam("projectId") String projectId, @RequestBody Task task) {
        System.out.println(projectId);
        return new ResponseEntity<>(new Task(), HttpStatus.OK);
    }
}
