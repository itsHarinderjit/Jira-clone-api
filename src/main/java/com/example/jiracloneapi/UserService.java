package com.example.jiracloneapi;

import com.sun.tools.jconsole.JConsoleContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MongoTemplate mongoTemplate;
    public Optional<User> getUserByUserName(String name) {
        Optional<User> user = Optional.empty();
        user = userRepository.findByName(name);
        return user;
    }
}
