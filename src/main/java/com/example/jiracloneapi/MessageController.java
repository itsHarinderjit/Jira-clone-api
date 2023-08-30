package com.example.jiracloneapi;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

@Controller
public class MessageController {
    @Autowired
    SendDataService sendDataService;

    @MessageMapping("/user/{userName}")
    @SendTo("/topic/{userName}")  // will broadcast the message to all subscribed users
    public ResponseEntity<Optional<SendData>> sendUser(String userName) throws JsonProcessingException {
            System.out.println("Sending data...");
            return new ResponseEntity<>(sendDataService.getUserData(userName),HttpStatus.OK);
    }


//    @MessageMapping("/user/{id}")
//    @SendTo("/topic/{id}")
//    public String sendProj(@DestinationVariable String id) {
//        return "Hello " + id + " project for you";
//    }
}
