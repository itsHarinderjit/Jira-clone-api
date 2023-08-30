package com.example.jiracloneapi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Reference;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "tasks")
public class Task {
    @Id
    private ObjectId id;
    private String taskId;
    private String heading;
    private String description;
    private String type;
    private String status;
    private String reporter;
    private List<String> assignees;
    private String priority;
    private int orgEstTime;
    private int timeSpent;
    private int timeRemaining;
    private Date createdOn;
    private Date updatedOn;
    @Reference
    private List<Comment> comments;
}
