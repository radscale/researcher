package com.researcher.university.api;

import com.researcher.university.domain.Task;
import com.researcher.university.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class TaskController {
    @Autowired
    TaskService taskService;


    @GetMapping("/projects/{projectId}/tasks")
    public List<Task> getTaskForProjects(@PathVariable Long projectId) {
        return taskService.getTaskForProjects(projectId);
    }

    @PostMapping("/projects/{projectId}/tasks")
    public Task addTask(@PathVariable Long projectId, @Valid @RequestBody Task task) {
        return taskService.createTask(projectId, task);
    }

    @PutMapping("/projects/{projectId}/tasks/{taskId}")
    public Task updateTask(@PathVariable Long projectId, @PathVariable Long taskId, @Valid @RequestBody Task task) {
        return taskService.updateTask(projectId, taskId, task);
    }

    @DeleteMapping("/projects/{projectId}/tasks/{taskId}")
    public ResponseEntity<?> deleteTask(@PathVariable Long projectId, @PathVariable Long taskId) {
        return taskService.deleteTask(projectId, taskId);
    }
}
