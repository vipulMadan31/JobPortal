package com.example.demo.entity;

import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;

@Entity
@Table(name="recruiters")
public class Recruiter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

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
