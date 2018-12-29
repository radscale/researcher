package com.researcher.university.repository;

import com.researcher.university.domain.AdHocTask;
import com.researcher.university.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface AdHocTaskRepository extends JpaRepository<AdHocTask, Long> {
    List<AdHocTask> findByUserId(Long userId);
}
