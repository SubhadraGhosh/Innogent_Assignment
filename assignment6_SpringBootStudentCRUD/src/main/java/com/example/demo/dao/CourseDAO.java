package com.example.demo.dao;

import com.example.demo.entity.CourseEntity;
import com.example.demo.repo.CourseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CourseDAO {

    private final CourseRepository repo;

    public CourseDAO(CourseRepository repo) {
        this.repo = repo;
    }

    public List<CourseEntity> findAll() {
        return repo.findAll();
    }

    public CourseEntity save(CourseEntity course) {
        return repo.save(course);
    }

    public Optional<CourseEntity> findById(Long id) {
        return repo.findById(id);
    }

    public void deleteById(Long id) {
        repo.deleteById(id);
    }

    public List<CourseEntity> findCoursesWithoutStudents() {
        return repo.findByStudentsIsEmpty();
    }
}