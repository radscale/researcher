package com.researcher.university.domain;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "User")
public class User {

    public User(){}

    public User(Long id, @NotBlank String title, @NotBlank String name, @NotBlank String lastName, @NotBlank String email, @NotBlank String password) {
        this.id = id;
        this.title = title;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @NotBlank
    @Column(name = "Title")
    private String title;

    @NotBlank
    @Column(name = "Name")
    private String name;

    @NotBlank
    @Column(name = "LastName")
    private String lastName;

    @NotBlank
    @Email
    @Column(name = "Email", unique = true)
    private String email;

    @NotBlank
    @Column(name = "Password")
    private String password;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!getId().equals(user.getId())) return false;
        if (!getTitle().equals(user.getTitle())) return false;
        if (!getName().equals(user.getName())) return false;
        if (!getLastName().equals(user.getLastName())) return false;
        if (!getEmail().equals(user.getEmail())) return false;
        return getPassword().equals(user.getPassword());
    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + getTitle().hashCode();
        result = 31 * result + getName().hashCode();
        result = 31 * result + getLastName().hashCode();
        result = 31 * result + getEmail().hashCode();
        result = 31 * result + getPassword().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
