package com.researcher.university.service;

import com.researcher.university.domain.Task;
import com.researcher.university.exception.ResourceNotFoundException;
import com.researcher.university.repository.ProjectRepository;
import com.researcher.university.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ProjectRepository projectRepository;


    public List<Task> getTaskForProjects(Long projectId) {
        return taskRepository.findByProjectId(projectId);
    }

    public Task createTask(Long projectId, @Valid Task task) {
        return projectRepository.findById(projectId)
                .map(project -> {
                    task.setProject(project);
                    return taskRepository.save(task);
                }).orElseThrow(() -> new ResourceNotFoundException("Project not found with id " + projectId));
    }

    public Task updateTask(Long projectId, Long taskId, @Valid Task taskRequest) {
        if(!projectRepository.existsById(projectId)) {
            throw new ResourceNotFoundException("Project not found with id " + projectId);
        }
        return taskRepository.findById(taskId)
                .map(task -> {
                    task.setDescription(taskRequest.getDescription());
                    task.setName(taskRequest.getName());
                    task.setStatus(taskRequest.getStatus());
                    task.setStartDate(taskRequest.getStartDate());
                    task.setFinishDate(taskRequest.getFinishDate());
                    return taskRepository.save(task);
                }).orElseThrow(() -> new ResourceNotFoundException("Task not found with id " + taskId));
    }

    public ResponseEntity<?> deleteTask(Long projectId, Long taskId) {
        if(!projectRepository.existsById(projectId)) {
            throw new ResourceNotFoundException("Project not found with id " + projectId);
        }

        return taskRepository.findById(taskId)
                .map(task -> {
                    taskRepository.delete(task);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Task not found with id " + taskId));
    }
}
