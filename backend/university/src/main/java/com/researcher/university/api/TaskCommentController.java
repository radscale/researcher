package com.researcher.university.api;

import com.researcher.university.domain.Task;
import com.researcher.university.domain.TaskComment;
import com.researcher.university.service.TaskCommentService;
import com.researcher.university.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class TaskCommentController {

    @Autowired
    TaskCommentService taskCommentService;

    @GetMapping("/projects/{projectId}/tasks/{taskId}/comments")
    public List<TaskComment> getCommentsForTask(@PathVariable Long projectId, @PathVariable Long taskId) {
        return taskCommentService.getCommentsForTask(projectId, taskId);
    }

    @PostMapping("/projects/{projectId}/tasks/{taskId}/comments")
    public TaskComment addTask(@PathVariable Long projectId, @PathVariable Long taskId, @Valid @RequestBody TaskComment comment) {
        return taskCommentService.createTaskComment(projectId, taskId, comment);
    }

    @PutMapping("/projects/{projectId}/tasks/{taskId}{taskId}/comments/{commentId}")
    public TaskComment updateTask(@PathVariable Long projectId, @PathVariable Long taskId, @PathVariable Long commentId, @Valid @RequestBody TaskComment comment) {
        return taskCommentService.updateTaskComment(projectId, taskId, commentId, comment);
    }

    @DeleteMapping("/projects/{projectId}/tasks/{taskId}/comments/{commentId}")
    public ResponseEntity<?> deleteTask(@PathVariable Long projectId, @PathVariable Long taskId, @PathVariable Long commentId) {
        return taskCommentService.deleteTaskComment(projectId, taskId, commentId);
    }
}
