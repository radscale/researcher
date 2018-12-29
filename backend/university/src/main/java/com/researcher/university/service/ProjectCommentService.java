package com.researcher.university.service;

import com.researcher.university.domain.ProjectComment;
import com.researcher.university.domain.Task;
import com.researcher.university.exception.ResourceNotFoundException;
import com.researcher.university.repository.ProjectCommentRepository;
import com.researcher.university.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.LinkedList;
import java.util.List;

@Service
public class ProjectCommentService {
    @Autowired
    ProjectCommentRepository projectCommentRepository;

    @Autowired
    ProjectRepository projectRepository;

    public List<ProjectComment> getTaskForProjects(Long projectId) {
        return projectCommentRepository.findByProjectId(projectId);
    }

    public ProjectComment createProjectComment(Long projectId, @Valid ProjectComment projectComment) {
        return projectRepository.findById(projectId)
                .map(project -> {
                    projectComment.setProject(project);
                    List<ProjectComment> projectComments = project.getProjectComments();
                    projectComments.add(projectComment);
                    project.setProjectComments(projectComments);
                    return projectCommentRepository.save(projectComment);
                }).orElseThrow(() -> new ResourceNotFoundException("Project not found with id " + projectId));

    }

    public ProjectComment updateProjectComment(Long projectId, Long projectCommentId, @Valid ProjectComment projectCommentRequest) {
        if(!projectRepository.existsById(projectId)) {
            throw new ResourceNotFoundException("Project not found with id " + projectId);
        }
        return projectCommentRepository.findById(projectCommentId)
                .map(comment -> {
                    comment.setCreationDate(projectCommentRequest.getCreationDate());
                    comment.setText(projectCommentRequest.getText());
                    comment.setProject(projectCommentRequest.getProject());
                    return projectCommentRepository.save(comment);
                }).orElseThrow(() -> new ResourceNotFoundException("Comment not found with id " + projectCommentId));
    }

    public ResponseEntity<?> deleteProjectCommment(Long projectId, Long projectCommentId) {
        if(!projectRepository.existsById(projectId)) {
            throw new ResourceNotFoundException("Project not found with id " + projectId);
        }

        return projectCommentRepository.findById(projectCommentId)
                .map(comment -> {
                    projectCommentRepository.delete(comment);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Comment not found with id " + projectCommentId));
    }
}
