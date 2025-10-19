package com.example.demo.dao;

import com.example.demo.entity.StudentEntity;
import com.example.demo.repo.StudentRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class StudentDAO {

    private final StudentRepository repo;

    public StudentDAO(StudentRepository repo) {
        this.repo = repo;
    }

    public List<StudentEntity> findAll() {
        return repo.findAll();
    }

    public StudentEntity save(StudentEntity s) {
        return repo.save(s);
    }

    public Optional<StudentEntity> findById(Long id) {
        return repo.findById(id);
    }

    public void deleteById(Long id) {
        repo.deleteById(id);
    }

    public List<StudentEntity> findByCourseName(String courseName) {
        return repo.findByCourse_CourseName(courseName);
    }

    public List<StudentEntity> findStudentsWithoutCourse() {
        return repo.findByCourseIsNull();
    }

    public List<StudentEntity> findByCityAndInstructor(String city, String instructor) {
        return repo.findByCityAndCourse_Instructor(city, instructor);
    }
}