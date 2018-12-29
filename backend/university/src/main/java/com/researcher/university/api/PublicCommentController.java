package com.researcher.university.api;

import com.researcher.university.domain.PublicComment;
import com.researcher.university.domain.User;
import com.researcher.university.service.PublicCommentService;
import com.researcher.university.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class PublicCommentController {
    @Autowired
    PublicCommentService publicCommentService;

    @GetMapping("/publicComments")
    public Page<PublicComment> getAllPublicCommentts(Pageable pageable) {
        return publicCommentService.getAllPublicComments(pageable);
    }

    @PostMapping("/publicComments")
    public PublicComment createPublicComment(@Valid @RequestBody PublicComment publicComment) {
        return publicCommentService.createPublicComment(publicComment);
    }

    @PutMapping("/publicComments/{commentId}")
    public PublicComment updateUser(@PathVariable Long commentId, @Valid @RequestBody PublicComment commentRequest) {
        return publicCommentService.updatePublicComment(commentId, commentRequest);
    }

    @DeleteMapping("/publicComments/{commentId}")
    public ResponseEntity<?> deletePublicComment(@PathVariable Long commentId) {
        return publicCommentService.deletePublicComment(commentId);
    }
}
