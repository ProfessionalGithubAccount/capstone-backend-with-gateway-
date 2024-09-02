package com.capstone.coursemicroservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Course {
    @Id
    private Long courseId;
    private String courseName;
    private String description;
    private Integer professorId;
    private String semester;
    private byte[] resource;
    private String imageUrl;
//    private byte[] resource;
//    private String studentIds; // Comma-separated string
//    private List<Integer> assignmentIds;

}
