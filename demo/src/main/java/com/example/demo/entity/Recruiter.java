package com.example.demo.entity;

import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;

@Entity
@Table(name="recruiters")
public class Recruiter {
    public Recruiter() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    public Recruiter(Integer id, String company, User user) {
        this.id = id;
        this.company = company;
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Recruiter{" +
                "id=" + id +
                ", company='" + company + '\'' +
                ", user=" + user +
                '}';
    }

    @Column(name = "company")
    private String company;

    @OneToOne
    @JoinColumn(name="user_id")
    private User user;


}
