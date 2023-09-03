package com.example.jiracloneapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@Controller
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @MessageMapping("/user/project/{id}")
    @SendTo("/topic/project/{id}")
    public ResponseEntity<Project> changeProj(@RequestBody Project project) {
        return new ResponseEntity<>(projectService.changeProjectData(project), HttpStatus.OK);
    }


}
