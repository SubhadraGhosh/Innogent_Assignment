package com.example.demo.service;

import com.example.demo.dao.CourseDAO;
import com.example.demo.dao.StudentDAO;
import com.example.demo.dto.CourseRequest;
import com.example.demo.dto.CourseResponse;
import com.example.demo.dto.UpdateInstructorRequest;
import com.example.demo.entity.CourseEntity;
import com.example.demo.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CourseService {

    private final CourseDAO courseDAO;
    private final StudentDAO studentDAO;

    public CourseService(CourseDAO courseDAO, StudentDAO studentDAO) {
        this.courseDAO = courseDAO;
        this.studentDAO = studentDAO;
    }

    // Create a new course
    @Transactional
    public CourseResponse createCourse(CourseRequest req) {
        CourseEntity course = CourseEntity.builder()
                .courseName(req.getCourseName())
                .instructor(req.getInstructor())
                .build();

        CourseEntity saved = courseDAO.save(course);
        return mapToResponse(saved);
    }

    // Get all courses with total student count
    @Transactional(readOnly = true)
    public List<CourseResponse> getAllCoursesWithCount() {
        return courseDAO.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Get all courses that have NO students
    @Transactional(readOnly = true)
    public List<CourseResponse> getCoursesWithoutStudents() {
        return courseDAO.findCoursesWithoutStudents().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Get student count for each course
    @Transactional(readOnly = true)
    public Map<String, Integer> getStudentCountForEachCourse() {
        List<CourseEntity> allCourses = courseDAO.findAll();
        Map<String, Integer> result = new LinkedHashMap<>();

        for (CourseEntity course : allCourses) {
            int count = (course.getStudents() == null) ? 0 : course.getStudents().size();
            result.put(course.getCourseName(), count);
        }
        return result;
    }

    // Update instructor name by courseId
    @Transactional
    public CourseResponse updateInstructor(Long courseId, UpdateInstructorRequest req) {
        CourseEntity course = courseDAO.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id " + courseId));

        course.setInstructor(req.getInstructor());
        CourseEntity updated = courseDAO.save(course);
        return mapToResponse(updated);
    }

    // Get top N courses by enrollment (sorted by student count)
    @Transactional(readOnly = true)
    public List<CourseResponse> getTopNCoursesByEnrollment(int n) {
        return courseDAO.findAll().stream()
                .sorted(Comparator.comparingInt(
                                (CourseEntity c) -> (c.getStudents() == null ? 0 : c.getStudents().size()))
                        .reversed())
                .limit(Math.max(0, n))
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Helper method to convert entity to response
    private CourseResponse mapToResponse(CourseEntity course) {
        int totalStudents = (course.getStudents() == null ? 0 : course.getStudents().size());

        return CourseResponse.builder()
                .id(course.getId())
                .courseName(course.getCourseName())
                .instructor(course.getInstructor())
                .totalStudents(totalStudents)
                .build();
    }
}