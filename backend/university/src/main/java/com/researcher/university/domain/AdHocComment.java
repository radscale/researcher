package com.researcher.university.domain;

import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
public class AdHocComment {

    public AdHocComment() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    private String text;

    @CreatedDate
    private Date creationDate;

    @ManyToOne
    private AdHocTask adHocTask;
}
