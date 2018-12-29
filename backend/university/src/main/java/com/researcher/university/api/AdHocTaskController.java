package com.researcher.university.api;

import com.researcher.university.domain.AdHocTask;
import com.researcher.university.domain.Task;
import com.researcher.university.service.AdHocTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class AdHocTaskController {
    @Autowired
    AdHocTaskService adHocTaskService;

    @GetMapping("/users/{userId}/adHocTasks")
    public List<AdHocTask> getAdHocTaskForUser(@PathVariable Long userId) {
        return adHocTaskService.getAdHocTaskForUser(userId);
    }

    @PostMapping("/users/{userId}/adHocTasks")
    public AdHocTask addTask(@PathVariable Long userId, @Valid @RequestBody AdHocTask task) {
        return adHocTaskService.createAdHocTask(userId, task);
    }

    @PutMapping("/users/{userId}/adHocTasks/{adHocTaskId}")
    public AdHocTask updateTask(@PathVariable Long userId, @PathVariable Long adHocTaskId, @Valid @RequestBody AdHocTask task) {
        return adHocTaskService.updateAdHocTask(userId, adHocTaskId, task);
    }

    @DeleteMapping("/users/{userId}/adHocTasks/{adHocTaskId}")
    public ResponseEntity<?> deleteTask(@PathVariable Long userId, @PathVariable Long adHocTaskId) {
        return adHocTaskService.deleteAdHocTask(userId, adHocTaskId);
    }
}
