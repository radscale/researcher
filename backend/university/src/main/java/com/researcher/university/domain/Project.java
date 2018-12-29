package com.researcher.university.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Project")
public class Project {

    public Project() {
    }

    public Project(@NotBlank String name, @NotBlank String description, Date startDate, Date finishDate) {
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.finishDate = finishDate;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    @Column(name = "Name")
    private String name;

    @NotBlank
    @Column(name = "Description")
    private String description;

    @Column(name = "StartDate")
    private Date startDate;

    @Column(name = "FinishDate")
    private  Date finishDate;

    @OneToMany
    @Column(name = "Tasks")
    private List<Task> tasks;

    @OneToMany
    @Column(name = "Comments")
    private List<ProjectComment> projectComments;

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public List<ProjectComment> getProjectComments() {
        return projectComments;
    }

    public void setProjectComments(List<ProjectComment> projectComments) {
        this.projectComments = projectComments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Project project = (Project) o;

        if (getId() != null ? !getId().equals(project.getId()) : project.getId() != null) return false;
        if (getName() != null ? !getName().equals(project.getName()) : project.getName() != null) return false;
        if (getDescription() != null ? !getDescription().equals(project.getDescription()) : project.getDescription() != null)
            return false;
        if (getStartDate() != null ? !getStartDate().equals(project.getStartDate()) : project.getStartDate() != null)
            return false;
        return getFinishDate() != null ? getFinishDate().equals(project.getFinishDate()) : project.getFinishDate() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        result = 31 * result + (getStartDate() != null ? getStartDate().hashCode() : 0);
        result = 31 * result + (getFinishDate() != null ? getFinishDate().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", startDate=" + startDate +
                ", finishDate=" + finishDate +
                '}';
    }
}
