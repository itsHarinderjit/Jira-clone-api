package com.example.jiracloneapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.FindAndReplaceOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public Optional<Project> findProjectByProjectId(String projectId) {
        return projectRepository.findProjectByProjectId(projectId);
    }

    public Project changeProjectData(Project project) {
        Query query = Query.query(Criteria.where("projectId").is(project.getProjectId()));
        Optional<Project> opProj = projectRepository.findProjectByProjectId(project.getProjectId());
        Project proj = null;
        if(opProj.isPresent())
            proj = opProj.get();
        assert proj != null;
        project.setId(proj.getId());
        project.setTasks(proj.getTasks());
        FindAndReplaceOptions options = new FindAndReplaceOptions().returnNew().upsert();
        return mongoTemplate.findAndReplace(query,project,options);
    }

    public Project addProject(Project project) {
//        for(String userId: project.getUsers()) {
//            Query query = new Query(Criteria.where("userId").is(userId));
//            Update update = new Update().push("projects",project.getProjectId());
//            mongoTemplate.findAndModify(query,update,User.class);
//        }
        Query query = new Query(Criteria.where("userId").in(project.getUsers()));
        Update update = new Update().push("projects",project.getProjectId());
        mongoTemplate.updateMulti(query,update,User.class);
        return mongoTemplate.save(project,"projects");
    }
}
