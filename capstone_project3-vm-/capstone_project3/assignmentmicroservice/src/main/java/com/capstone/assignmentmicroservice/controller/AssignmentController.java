package com.capstone.assignmentmicroservice.controller;

import com.capstone.assignmentmicroservice.entity.Assignment;
import com.capstone.assignmentmicroservice.service.AssignmentService;
//import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.core.io.Resource;


import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/assignments")
public class AssignmentController {
    @Autowired
    private AssignmentService assignmentService;



    @GetMapping("/showAll")
    public List<Map<String, Object>> getAllAssignments() {

        return assignmentService.getAllAssignments();
    }

    @GetMapping("/byCourseId/{courseId}")
    public List<Map<String, Object>> getAssignmentsByCourseId(@PathVariable Integer courseId) {
        return  assignmentService.getAssignmentsByCourseId(courseId);

    }

    @PostMapping("/upload")
    public String uploadAssignment(@RequestParam("file") MultipartFile file,
                                   @RequestParam("title") String title,@RequestParam("id") Integer id,@RequestParam("courseId") Integer courseId) {
        try {

//          assignmentService.changeColumnType();

            assignmentService.saveAssignment(file, title,id,courseId);
            return "Assignment uploaded successfully.";
        } catch (IOException e) {
            return "Failed to upload assignment: " + e.getMessage();
        }
    }


    @GetMapping("/showAssignmentFileById/{id}")
    public ResponseEntity<Resource> showAssignmentFileById(@PathVariable Integer id) {
        byte[] fileData = assignmentService.showAssignmentFileById(id);
        if (fileData == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        ByteArrayResource resource = new ByteArrayResource(fileData);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"assignment_" + id + ".pdf\"")
                .contentLength(fileData.length)
                .contentType(MediaType.APPLICATION_PDF)
                .body(resource);
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Integer id) {
        byte[] fileData = assignmentService.showAssignmentFileById(id);
        if (fileData == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        ByteArrayResource resource = new ByteArrayResource(fileData);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"assignment_" + id + ".pdf\"")
                .contentLength(fileData.length)
                .contentType(MediaType.APPLICATION_PDF)
                .body(resource);
    }
}

