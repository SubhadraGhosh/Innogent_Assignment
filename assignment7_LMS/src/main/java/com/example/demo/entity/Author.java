package com.example.demo.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Author {

     @Id
     @GeneratedValue(strategy= GenerationType.IDENTITY)
     private Integer id;

     private String name;
     private String email;

     @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "author")
     private List<Book> books;

    public Author(String name,String email) {
        this.name = name;
        this.email = email;
    }

}