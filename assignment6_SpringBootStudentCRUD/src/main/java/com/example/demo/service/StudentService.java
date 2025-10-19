package com.example.demo.service;

import com.example.demo.dao.CourseDAO;
import com.example.demo.dao.StudentDAO;
import com.example.demo.dto.StudentRequest;
import com.example.demo.dto.StudentResponse;
import com.example.demo.entity.CourseEntity;
import com.example.demo.entity.StudentEntity;
import com.example.demo.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentDAO studentDAO;
    private final CourseDAO courseDAO;

    public StudentService(StudentDAO studentDAO, CourseDAO courseDAO) {
        this.studentDAO = studentDAO;
        this.courseDAO = courseDAO;
    }

    @Transactional
    public StudentResponse createStudent(StudentRequest req) {
        StudentEntity s = StudentEntity.builder()
                .name(req.getName())
                .email(req.getEmail())
                .city(req.getCity())
                .build();

        if (req.getCourseId() != null) {
            CourseEntity c = courseDAO.findById(req.getCourseId())
                    .orElseThrow(() -> new ResourceNotFoundException("Course not found with id " + req.getCourseId()));
            s.setCourse(c);
        }

        StudentEntity saved = studentDAO.save(s);
        return mapToResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<StudentResponse> getAllStudentsWithCourseDetails() {
        return studentDAO.findAll().stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public StudentResponse getStudentById(Long id) {
        StudentEntity s = studentDAO.findById(id).orElseThrow(() -> new ResourceNotFoundException("Student not found with id " + id));
        return mapToResponse(s);
    }

    @Transactional
    public StudentResponse updateStudent(Long id, StudentRequest req) {
        StudentEntity s = studentDAO.findById(id).orElseThrow(() -> new ResourceNotFoundException("Student not found with id " + id));
        s.setName(req.getName());
        s.setEmail(req.getEmail());
        s.setCity(req.getCity());

        if (req.getCourseId() != null) {
            CourseEntity c = courseDAO.findById(req.getCourseId())
                    .orElseThrow(() -> new ResourceNotFoundException("Course not found with id " + req.getCourseId()));
            s.setCourse(c);
        } else {
            s.setCourse(null);
        }

        StudentEntity updated = studentDAO.save(s);
        return mapToResponse(updated);
    }

    @Transactional
    public void deleteStudent(Long id) {
        if (studentDAO.findById(id).isEmpty()) {
            throw new ResourceNotFoundException("Student not found with id " + id);
        }
        studentDAO.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<StudentResponse> getStudentsByCourseName(String courseName) {
        return studentDAO.findByCourseName(courseName).stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<StudentResponse> getStudentsWithoutCourse() {
        return studentDAO.findStudentsWithoutCourse().stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<StudentResponse> searchStudentsByCityAndInstructor(String city, String instructor) {
        return studentDAO.findByCityAndInstructor(city, instructor).stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    private StudentResponse mapToResponse(StudentEntity s) {
        Long courseId = null;
        String courseName = null;
        String instructor = null;
        if (s.getCourse() != null) {
            courseId = s.getCourse().getId();
            courseName = s.getCourse().getCourseName();
            instructor = s.getCourse().getInstructor();
        }
        return StudentResponse.builder()
                .id(s.getId())
                .name(s.getName())
                .email(s.getEmail())
                .city(s.getCity())
                .courseId(courseId)
                .courseName(courseName)
                .instructor(instructor)
                .build();
    }
}