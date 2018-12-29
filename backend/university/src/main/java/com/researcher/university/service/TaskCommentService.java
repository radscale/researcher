package com.researcher.university.service;

import com.researcher.university.domain.Task;
import com.researcher.university.domain.TaskComment;
import com.researcher.university.exception.ResourceNotFoundException;
import com.researcher.university.repository.ProjectRepository;
import com.researcher.university.repository.TaskCommentRepository;
import com.researcher.university.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.LinkedList;
import java.util.List;

@Service
public class TaskCommentService {


    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private TaskCommentRepository taskCommentRepository;


    public List<TaskComment> getCommentsForTask(Long projectId, Long taskId) {
            List<Task> tasks = taskRepository.findByProjectId(projectId);
            Task mainTask = new Task();
        for (Task task: tasks) {
            if(task.getId() == taskId)
                mainTask = task;
        };
        return mainTask.getComments();

       // return taskCommentRepository.findByTaskId(taskId);
    }

    public TaskComment createTaskComment(Long projectId, Long taskId, @Valid TaskComment task) {
//        return projectRepository.findById(projectId)
//                .map(project -> {
//                    task.setProject(project);
//                    List<Task> tasks = new LinkedList<>();
//                    tasks.add(task);
//                    project.setTasks(tasks);
//                    return taskRepository.save(task);
//                }).orElseThrow(() -> new ResourceNotFoundException("Project not found with id " + projectId));
        return null;
    }

    public TaskComment updateTaskComment(Long projectId, Long taskId, Long taskCommentId, @Valid TaskComment taskRequest) {
//        if(!projectRepository.existsById(projectId)) {
//            throw new ResourceNotFoundException("Project not found with id " + projectId);
//        }
//        return taskRepository.findById(taskId)
//                .map(task -> {
//                    task.setDescription(taskRequest.getDescription());
//                    task.setName(taskRequest.getName());
//                    task.setStatus(taskRequest.getStatus());
//                    task.setStartDate(taskRequest.getStartDate());
//                    task.setFinishDate(taskRequest.getFinishDate());
//                    return taskRepository.save(task);
//                }).orElseThrow(() -> new ResourceNotFoundException("Task not found with id " + taskId));
        return null;
    }

    public ResponseEntity<?> deleteTaskComment(Long projectId, Long taskId, Long taskCommentId) {
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
