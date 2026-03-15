package com.example.demo.entity;

import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Entity
@Table(name="jobs")
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="title")
    private String title;

    @Override
    public String toString() {
        return "Job{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", recruiter=" + recruiter +
                '}';
    }

    @Column(name="description")
    private String description;

    @Column(name="location")
    private String location;

    @Column(name="Salary")
    private Integer salary;

    @JoinColumn(name="recruiter_id")
    @ManyToOne
    private Recruiter recruiter;

    //there is one more reason for making the side which cannot exist alone the owner
    //you can have this (coz ek hi rec pe jaayega), so no need for any extra things
    //as we will have one sided relation, dont mention job in recruiter class

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public Job(Integer id, String title, String description, String location, Integer salary, Recruiter recruiter) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.salary = salary;
        this.recruiter = recruiter;
    }

    public Job(Integer id, String title, String description, Recruiter recruiter) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.recruiter = recruiter;
    }

    public Job() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Recruiter getRecruiter() {
        return recruiter;
    }

    public void setRecruiter(Recruiter recruiter) {
        this.recruiter = recruiter;
    }
}
