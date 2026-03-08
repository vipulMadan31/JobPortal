package com.example.demo.repository;

import com.example.demo.entity.Job;
import com.example.demo.entity.Recruiter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobRepository extends JpaRepository<Job, Integer> {
    public List<Job> findByRecruiter(Recruiter recruiter);
    //here, we write list coz of one to many mapping
}
