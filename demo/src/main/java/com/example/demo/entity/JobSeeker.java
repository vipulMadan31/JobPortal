package com.example.demo.entity;


import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;

@Entity
@Table(name="job_seekers")
public class JobSeeker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name="college")
    private String college;

    @OneToOne
    @JoinColumn(name="user_id")
    private User user;

    @Override
    public String toString() {
        return "JobSeeker{" +
                "id=" + id +
                ", college='" + college + '\'' +
                ", user=" + user +
                '}';
    }

    public JobSeeker() {
    }

    public JobSeeker(Integer id, String college, User user) {
        this.id = id;
        this.college = college;
        this.user = user;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}