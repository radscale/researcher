package com.researcher.university.service;

import com.researcher.university.domain.PublicComment;
import com.researcher.university.domain.User;
import com.researcher.university.exception.ResourceNotFoundException;
import com.researcher.university.repository.PublicCommentRepository;
import com.researcher.university.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Service
public class PublicCommentService {
    @Autowired
    private PublicCommentRepository publicCommentRepository;

    public Page<PublicComment> getAllPublicComments(Pageable pageable) {
        return publicCommentRepository.findAll(pageable);
    }

    public PublicComment createPublicComment(@Valid PublicComment comment) {
        return publicCommentRepository.save(comment);
    }

    public PublicComment updatePublicComment(Long commentId, @Valid PublicComment commentRequest) {
        return publicCommentRepository.findById(commentId).map(comment -> {
            comment.setText(commentRequest.getText());
            comment.setCreationDate(commentRequest.getCreationDate());
            return publicCommentRepository.save(comment);
        }).orElseThrow(() -> new ResourceNotFoundException("CommentId " + commentId + " not found"));
    }

    public ResponseEntity<?> deletePublicComment(Long commentId) {
        return publicCommentRepository.findById(commentId).map(comment -> {
            publicCommentRepository.delete(comment);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("CommentId " + commentId + " not found"));
    }
}
