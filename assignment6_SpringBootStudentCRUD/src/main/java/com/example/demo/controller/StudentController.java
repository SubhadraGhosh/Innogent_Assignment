package com.example.demo.controller;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.entity.Student;
import com.example.demo.service.IStudentService;



@RestController
@RequestMapping("/controller")
public class StudentController {
     
	 @Autowired
	 private IStudentService service;
	 
	 @PostMapping("/insert")
	 public ResponseEntity<Student> create( @RequestBody Student student){
		  System.out.println(student+"-------------");
		 return new ResponseEntity<>(service.insert(student),HttpStatus.CREATED);
	 }
	 
	 @GetMapping("/fetch")
	 public ResponseEntity<List<Student>> show(){
		 return new ResponseEntity<List<Student>>(service.show(),HttpStatus.OK);
	 }
	 
	 @PutMapping("/update/{id}")
	 public ResponseEntity<Student> update(@PathVariable("id")Integer id,  @RequestBody Student student){
		 return new ResponseEntity<Student>(service.update(id, student),HttpStatus.OK);
	 }
	 
	 @DeleteMapping("/delete/{id}")
	 public ResponseEntity<String> delete(@PathVariable Integer id){
		 return new ResponseEntity<String>(service.deleteStudent(id),HttpStatus.OK);
	 }
}
