package com.example.jiracloneapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SendDataService {
    @Autowired
    private UserService userService;
    @Autowired
    private ProjectService projectService;

    public Optional<SendData> getUserData(String userName) throws JsonProcessingException {
        System.out.println("Hello from send data");
        Optional<User> opUser = userService.getUserByUserName(userName);
        System.out.println(userName);
        User user = null;
        if(opUser.isPresent())
            user = opUser.get();
        else {
            System.out.println("User not found");
            return Optional.empty();
        }
        List<String> projects = user.getProjects();
        List<Project> userProjects = new ArrayList<>();
        for (String project:projects
             ) {
            Optional<Project> opProject = projectService.findProjectByProjectId(project);
            opProject.ifPresent(userProjects::add);
        }
        SendData data = new SendData(user,userProjects);
//        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
//        String json = "";
//        try {
//            json = ow.writeValueAsString(data);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
        System.out.println("Data sent");
        return Optional.of(data);
    }
}
