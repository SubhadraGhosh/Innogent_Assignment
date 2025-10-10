package com.example.demo.entity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "std_table")
public class Student {
       
	   @Id
	   @GeneratedValue(strategy = GenerationType.AUTO)
	   private Integer id;
	   
	   @Column(name = "std_name")
	   private String name;
	   
	   @Column(name = "std_email")
	   private String email;
}
