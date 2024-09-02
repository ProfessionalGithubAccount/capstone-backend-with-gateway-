package com.capstone.assignmentmicroservice.repository;


import com.capstone.assignmentmicroservice.entity.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssignmentRepository extends JpaRepository<Assignment, Integer> {
    List<Assignment> findByCourseId(Integer courseId);
}
