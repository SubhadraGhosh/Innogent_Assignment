package com.example.demo.controller;

import com.example.demo.dto.CourseRequest;
import com.example.demo.dto.CourseResponse;
import com.example.demo.dto.UpdateInstructorRequest;
import com.example.demo.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService service;

    public CourseController(CourseService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<CourseResponse> createCourse(@Valid @RequestBody CourseRequest req) {
        return ResponseEntity.ok(service.createCourse(req));
    }

    @GetMapping
    public ResponseEntity<List<CourseResponse>> getAllCourses() {
        return ResponseEntity.ok(service.getAllCoursesWithCount());
    }

    @GetMapping("/without-students")
    public ResponseEntity<List<CourseResponse>> getCoursesWithoutStudents() {
        return ResponseEntity.ok(service.getCoursesWithoutStudents());
    }

    @GetMapping("/student-count")
    public ResponseEntity<Map<String, Integer>> getStudentCountPerCourse() {
        return ResponseEntity.ok(service.getStudentCountForEachCourse());
    }

    @PutMapping("/{id}/instructor")
    public ResponseEntity<CourseResponse> updateInstructor(@PathVariable Long id, @Valid @RequestBody UpdateInstructorRequest req) {
        return ResponseEntity.ok(service.updateInstructor(id, req));
    }

    @GetMapping("/top/{n}")
    public ResponseEntity<List<CourseResponse>> getTopN(@PathVariable int n) {
        return ResponseEntity.ok(service.getTopNCoursesByEnrollment(n));
    }
}