package com.researcher.university.repository;

import com.researcher.university.domain.Project;
import com.researcher.university.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>{
    List<Task> findByProjectId(Long projectId);
}
