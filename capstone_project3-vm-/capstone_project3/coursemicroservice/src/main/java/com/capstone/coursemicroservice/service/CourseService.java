package com.capstone.coursemicroservice.service;

import com.capstone.coursemicroservice.entity.Course;
import com.capstone.coursemicroservice.repository.CourseRepository;
//import org.hibernate.sql.ast.tree.update.Assignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class  CourseService {
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private RestTemplate restTemplate;

    public List<Course> getAllCourses() {

        return courseRepository.findAll();
    }

    public Course getCourseById(Integer courseId) {

        return courseRepository.findById(courseId).orElse(null);
    }

    public Course saveCourse(Course course) {

        return courseRepository.save(course);
    }

    public Course updateCourse(Course course) {

        return courseRepository.save(course);
    }


    public List getAllCoursesByStudentId(Integer studentId) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huMUBleGFtLmNvbSIsImV4cCI6MTcyNTExOTg3MSwiaWF0IjoxNzI1MDgzODcxfQ.stUdgknSCdNLW5OcxOUMnPK7PQ_WFL-WdqAwO2H9blM");

        headers.set("Content-Type", "application/json");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        String studentServiceUrl = "http://localhost:8761/eureka/students/" + studentId + "/courses";

        ResponseEntity<List> response = restTemplate.exchange(
                studentServiceUrl,
                HttpMethod.GET,
                entity,
                List.class
        );
        return courseRepository.findAllById(response.getBody());
    }




    public List<Course> getAllCoursesByProfessorId(Integer professorId,String bearerToken) {
        String token="Bearer "+bearerToken;
        System.out.println(token);
        HttpHeaders headers = new HttpHeaders();
        //headers.set("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huMUBleGFtLmNvbSIsImV4cCI6MTcyNTExOTg3MSwiaWF0IjoxNzI1MDgzODcxfQ.stUdgknSCdNLW5OcxOUMnPK7PQ_WFL-WdqAwO2H9blM");
        //Try-1 ..to call bearer token directly ..
        headers.set("Authorization", token);

        headers.set("Content-Type", "application/json");

        HttpEntity<String> entity = new HttpEntity<>(headers);
        String teacherServiceUrl = "http://localhost:8761/eureka/professor/" + professorId + "/courses"; // Adjust the URL as necessary
        List<Integer> courseIds = restTemplate.getForObject(teacherServiceUrl, List.class);

        return courseRepository.findAllById(courseIds);
    }

    public byte[] getResourceByCourseId(Integer courseId) {
        Course course = courseRepository.findById(courseId).orElse(null);
        return course != null ? course.getResource() : null;
    }


//    public void deleteCourse(Integer courseId) {
//        courseRepository.deleteById(courseId);
//    }
//
//    public List<Course> getRecentCourses() {
//        // Dummy implementation, adjust based on real requirements
//        return courseRepository.findTop5ByOrderByLastAccessedDesc(); // Fetch all courses for now
//    }

//    public String getProfessorNameById(Integer professorId) {
//        String url = "http://professor-service/professors/" + professorId;
//        return restTemplate.getForObject(url, String.class);
//    }

//    public List<Integer> getAssignmentIdsByCourseId(Integer courseId) {
//        Optional<Course> course = courseRepository.findById(courseId);
//        if (course.isPresent()) {
//            return course.get().getAssignmentIds(); // Assuming getAssignmentIds returns a List<Integer>
//        } else {
//            throw new RuntimeException("My friend the problem is ..Course not found with id " + courseId);
//        }
//    }


//
//    public int getOverallScore(Integer courseId) {
//        int totalScore = 0;
//        Course course = courseRepository.findById(courseId)
//                .orElseThrow(() -> new RuntimeException(" My friend the problem is ..Course not found with id " + courseId));
//
//        // Assuming assignmentIds is stored as a comma-separated string
//        List<Integer> assignmentIds = course.getAssignmentIds();
//                /*
//                    if working with strings instead of List<Integer> for assignmentids use this
//                    List<Long> assignmentIds = Arrays.stream(assignmentIdsString.split(","))
//                                         .map(Long::parseLong)
//                                         .collect(Collectors.toList());
//                */
//        for (Integer assignmentId : assignmentIds) {
//            // Call the Assignment microservice to get the score for each assignment
//            String url = "http://localhost:5002/assignments/getScore/" + assignmentId;
//            Integer score = restTemplate.getForObject(url, Integer.class);
//            if (score != null) {
//                totalScore += score;
//            }
//        }
//        return totalScore;
//    }
}
