package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name="applications")
public class Application {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="status")
    private String status;

    @Override
    public String toString() {
        return "Application{" +
                "id=" + id +
                ", status='" + status + '\'' +
                ", job=" + job +
                ", jobSeeker=" + jobSeeker +
                '}';
    }

    @OneToOne
    @JoinColumn(name="job_id")
    private Job job;

    @OneToOne
    @JoinColumn(name="seeker_id")
    private JobSeeker jobSeeker;
}
