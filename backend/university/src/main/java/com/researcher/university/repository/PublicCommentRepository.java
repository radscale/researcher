package com.researcher.university.repository;

import com.researcher.university.domain.PublicComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublicCommentRepository extends JpaRepository<PublicComment, Long> {
}
