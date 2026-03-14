package com.example.demo.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name="applications")
public class Application {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    public Integer getId() {
        return id;
    }

    public Application() {
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public JobSeeker getJobSeeker() {
        return jobSeeker;
    }

    public void setJobSeeker(JobSeeker jobSeeker) {
        this.jobSeeker = jobSeeker;
    }

    @Column(name="status")
    private String status;

    @Column(name="creation_time")
    LocalDateTime creationTime;


    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

    @Override
    public String toString() {
        return "Application{" +
                "id=" + id +
                ", status='" + status + '\'' +
                ", creationTime=" + creationTime +
                ", job=" + job +
                ", jobSeeker=" + jobSeeker +
                '}';
    }

    public Application(String status, LocalDateTime creationTime, Job job, JobSeeker jobSeeker) {
        this.status = status;
        this.creationTime = creationTime;
        this.job = job;
        this.jobSeeker = jobSeeker;
    }

    @OneToOne
    @JoinColumn(name="job_id")
    private Job job;

    @OneToOne
    @JoinColumn(name="seeker_id")
    private JobSeeker jobSeeker;
}
