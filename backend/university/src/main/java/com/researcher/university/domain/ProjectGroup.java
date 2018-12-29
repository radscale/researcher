package com.researcher.university.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
public class ProjectGroup {

    public ProjectGroup() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    private String name;

    @ManyToMany
    private List<User> users;

    @ManyToMany
    private List<Project> projects;
}

