package com.example.jiracloneapi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Reference;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "projects")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Project {
    @Id
    private ObjectId id;
    private String projectId;
    private String name;
    private String description;
    @Reference
    private List<Task> tasks;
    private List<String> users;
    private String type;
    private String projectImg;
}
