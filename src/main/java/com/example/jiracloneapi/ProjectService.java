package com.example.jiracloneapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.OptionalInt;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    public Optional<Project> findProjectByProjectId(String projectId) {
        return projectRepository.findProjectByProjectId(projectId);
    }
}
