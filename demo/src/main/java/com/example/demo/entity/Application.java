package com.example.demo.entity;

import jakarta.persistence.*;

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

    public Application(Integer id, String status, Job job, JobSeeker jobSeeker) {
        this.id = id;
        this.status = status;
        this.job = job;
        this.jobSeeker = jobSeeker;
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

    public Application(String status, Job job, JobSeeker jobSeeker) {
        this.status = status;
        this.job = job;
        this.jobSeeker = jobSeeker;
    }

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
