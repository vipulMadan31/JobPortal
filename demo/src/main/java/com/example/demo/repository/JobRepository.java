package com.example.demo.repository;

import com.example.demo.entity.Job;
import com.example.demo.entity.Recruiter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobRepository extends JpaRepository<Job, Integer> {
    public List<Job> findByRecruiter(Recruiter recruiter);
    //here, we write list coz of one to many mapping

    Page<Job> findByTitleContainingIgnoreCaseOrRecruiterCompanyContainingIgnoreCaseOrLocationContainingIgnoreCase(
            String title, String company, String location, Pageable pageable
    );
    //note that all our queries now go through this, so adding pagebale here and page instead of list is enough


}
