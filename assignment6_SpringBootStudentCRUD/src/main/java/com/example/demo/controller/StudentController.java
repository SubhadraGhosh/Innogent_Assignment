package com.example.demo.controller;

import com.example.demo.dto.StudentRequest;
import com.example.demo.dto.StudentResponse;
import com.example.demo.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<StudentResponse> createStudent(@Valid @RequestBody StudentRequest req) {
        return ResponseEntity.ok(service.createStudent(req));
    }

    @GetMapping
    public ResponseEntity<List<StudentResponse>> getAllStudents() {
        return ResponseEntity.ok(service.getAllStudentsWithCourseDetails());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getStudentById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentResponse> updateStudent(@PathVariable Long id, @Valid @RequestBody StudentRequest req) {
        return ResponseEntity.ok(service.updateStudent(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable Long id) {
        service.deleteStudent(id);
        return ResponseEntity.ok(Map.of("message", "Student deleted successfully"));
    }

    @GetMapping("/byCourse/{courseName}")
    public ResponseEntity<List<StudentResponse>> getByCourse(@PathVariable String courseName) {
        return ResponseEntity.ok(service.getStudentsByCourseName(courseName));
    }

    @GetMapping("/noCourse")
    public ResponseEntity<List<StudentResponse>> getStudentsWithoutCourse() {
        return ResponseEntity.ok(service.getStudentsWithoutCourse());
    }

    @GetMapping("/search")
    public ResponseEntity<List<StudentResponse>> searchByCityAndInstructor(@RequestParam String city, @RequestParam String instructor) {
        return ResponseEntity.ok(service.searchStudentsByCityAndInstructor(city, instructor));
    }
}