package com.example.demo.service;
import java.util.List;

import com.example.demo.entity.Student;

public interface IStudentService {
     
	  public Student insert(Student student);
	  public List<Student> show();
	  public String deleteStudent(Integer id);
	  public Student update(Integer id,Student student);
}
