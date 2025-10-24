package com.example.demo.controller;

import com.example.demo.dto.AuthorRequest;
import com.example.demo.dto.AuthorResponse;
import com.example.demo.entity.Author;
import com.example.demo.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/author")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @PostMapping("/addAuthor")
    public ResponseEntity<AuthorResponse> addAuthor(@RequestBody AuthorRequest authorRequest){

        AuthorResponse insert = authorService.addAuthor(authorRequest);
        return new ResponseEntity<>(insert, HttpStatus.CREATED);
    }

    @GetMapping("/showAllAuthor")
    public ResponseEntity<List<AuthorResponse>> showALlAuthor(){
        return new ResponseEntity<>(authorService.showAllAuthor(), HttpStatus.OK);
    }


    @GetMapping("/getAuthorById/{id}")
    public ResponseEntity<AuthorResponse> getAuthorById(@PathVariable("id")Integer id){
        return new ResponseEntity<>(authorService.getAuthorById(id), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<AuthorResponse>updateAuthor(@PathVariable("id")Integer id,@RequestBody AuthorRequest request){
        return new ResponseEntity<>(authorService.updateAuthor(id, request), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String>deleteAuthor(@PathVariable("id")Integer id){
        return new ResponseEntity<>(authorService.deleteAuthor(id), HttpStatus.OK);
    }

}