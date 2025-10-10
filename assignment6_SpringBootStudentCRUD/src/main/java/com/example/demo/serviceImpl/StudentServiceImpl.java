package com.example.demo.serviceImpl;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.entity.Student;
import com.example.demo.repo.IStudentRepository;
import com.example.demo.service.IStudentService;

@Service
public class StudentServiceImpl implements IStudentService {
    
	@Autowired
	private IStudentRepository repo;
	
	@Override
	public Student insert(Student student) {
	
		return repo.save(student);
	}

	@Override
	public List<Student> show() {
		
		return repo.findAll();
	}

	@Override
	public String deleteStudent(Integer id) {
	     boolean existsById = repo.existsById(id);
	     if(existsById) {
	    	 repo.deleteById(id); 
	     }
		 
		return "id is not represent";
	}

	@Override
	public Student update(Integer id,Student student) {
		
		Student std = repo.findById(id).get();
		if(std!=null) {
			std.setName(student.getName());
			std.setEmail(student.getEmail());
			
		}
		return repo.save(std);
	}

}
