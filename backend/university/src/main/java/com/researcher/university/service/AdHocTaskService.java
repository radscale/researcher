package com.researcher.university.service;

import com.researcher.university.domain.AdHocTask;
import com.researcher.university.domain.Task;
import com.researcher.university.exception.ResourceNotFoundException;
import com.researcher.university.repository.AdHocTaskRepository;
import com.researcher.university.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.LinkedList;
import java.util.List;

@Service
public class AdHocTaskService {
    @Autowired
    private AdHocTaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    public List<AdHocTask> getAdHocTaskForUser(Long userId) {
        return taskRepository.findByUserId(userId);
    }

    public AdHocTask createAdHocTask(Long userId, @Valid AdHocTask task) {
        return userRepository.findById(userId)
                .map(user -> {
                    task.setOwner(user);
                    List<AdHocTask> tasks = new LinkedList<>();
                    tasks.add(task);
                    user.setAdHocTasks(tasks);
                    return taskRepository.save(task);
                }).orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));
    }

    public AdHocTask updateAdHocTask(Long userId, Long taskId, @Valid AdHocTask taskRequest) {
        if(!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with id " + userId);
        }
        return taskRepository.findById(taskId)
                .map(task -> {
                    task.setDescription(taskRequest.getDescription());
                    task.setName(taskRequest.getName());
                    task.setStatus(taskRequest.getStatus());
                    task.setStartDate(taskRequest.getStartDate());
                    task.setFinishDate(taskRequest.getFinishDate());
                    task.setOwner(taskRequest.getOwner());
                    return taskRepository.save(task);
                }).orElseThrow(() -> new ResourceNotFoundException("Task not found with id " + taskId));
    }

    public ResponseEntity<?> deleteAdHocTask(Long userId, Long taskId) {
        if(!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with id " + userId);
        }

        return taskRepository.findById(taskId)
                .map(task -> {
                    taskRepository.delete(task);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Task not found with id " + taskId));
    }
}
