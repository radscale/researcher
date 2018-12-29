package com.researcher.university.api;

import com.researcher.university.domain.ProjectComment;
import com.researcher.university.domain.Task;
import com.researcher.university.service.ProjectCommentService;
import com.researcher.university.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ProjectCommentController {
    @Autowired
    ProjectCommentService projectCommentService;

    @GetMapping("/projects/{projectId}/comments")
    public List<ProjectComment> getCommentsForProjects(@PathVariable Long projectId) {
        return projectCommentService.getTaskForProjects(projectId);
    }

    @PostMapping("/projects/{projectId}/comments")
    public ProjectComment addComment(@PathVariable Long projectId, @Valid @RequestBody ProjectComment projectComment) {
        return projectCommentService.createProjectComment(projectId, projectComment);
    }

    @PutMapping("/projects/{projectId}/comments/{projectCommentId}")
    public ProjectComment updateComment(@PathVariable Long projectId, @PathVariable Long projectCommentId, @Valid @RequestBody ProjectComment projectComment) {
        return projectCommentService.updateProjectComment(projectId, projectCommentId, projectComment);
    }

    @DeleteMapping("/projects/{projectId}/comments/{projectCommentId")
    public ResponseEntity<?> deleteComment(@PathVariable Long projectId, @PathVariable Long projectCommentId) {
        return projectCommentService.deleteProjectCommment(projectId, projectCommentId);
    }
}
