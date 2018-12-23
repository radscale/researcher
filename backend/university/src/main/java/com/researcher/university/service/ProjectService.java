package com.researcher.university.service;

import com.researcher.university.domain.Project;
import com.researcher.university.exception.ResourceNotFoundException;
import com.researcher.university.repository.ProjectRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public Page<Project> getAllProjects(org.springframework.data.domain.Pageable pageable) {return projectRepository.findAll(pageable); }

    public Project createProject(@Valid Project project) {
        return projectRepository.save(project);
    }

    public Project updateProject(Long projectId, @Valid Project projectRequest) {
        return projectRepository.findById(projectId).map(project -> {
            project.setDescription(projectRequest.getDescription());
            project.setFinishDate(projectRequest.getFinishDate());
            project.setName(projectRequest.getName());
            project.setStartDate(projectRequest.getStartDate());
            return projectRepository.save(project);
        }).orElseThrow(() -> new ResourceNotFoundException("ProjectId " + projectId + " not found"));
    }

    public ResponseEntity<?> deleteProject(Long projectId) {
        return projectRepository.findById(projectId).map(project -> {
            projectRepository.delete(project);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("ProjectId " + projectId + " not found"));
    }
}
