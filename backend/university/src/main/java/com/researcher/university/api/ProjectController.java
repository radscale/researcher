package com.researcher.university.api;

import com.researcher.university.domain.Project;
import com.researcher.university.domain.User;
import com.researcher.university.service.ProjectService;
import com.researcher.university.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class ProjectController {
    @Autowired
    ProjectService projectService;

    @GetMapping("/projects")
    public Page<Project> getAllProjects(Pageable pageable) {
        return projectService.getAllProjects(pageable);
    }

    @PostMapping("/projects")
    public Project createProject(@Valid @RequestBody Project project) {
        return projectService.createProject(project);
    }

    @PutMapping("/projects/{projectId}")
    public Project updateProject(@PathVariable Long projectId, @Valid @RequestBody Project projectRequest) {
        return projectService.updateProject(projectId, projectRequest);
    }

    @DeleteMapping("/projects/{projectId}")
    public ResponseEntity<?> deleteProject(@PathVariable Long projectId) {
        return projectService.deleteProject(projectId);
    }
}
