package com.researcher.university.repository;

import com.researcher.university.domain.ProjectGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<ProjectGroup, Long> {
}
