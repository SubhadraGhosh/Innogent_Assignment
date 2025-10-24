package com.example.demo.controller;
import java.util.List;

import com.example.demo.dto.BookResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.BookRequest;
import com.example.demo.dto.BorrowedRequest;
import com.example.demo.dto.ShowBorrowedBooksResponse;
import com.example.demo.dto.BookResponse;
import com.example.demo.dto.BorrowedResponse;
import com.example.demo.service.BookService;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping("/addBook")
    public ResponseEntity<BookResponse> addBook(@RequestBody BookRequest request){
        return new ResponseEntity<>(bookService.addBook(request), HttpStatus.CREATED);
    }

    @GetMapping("/showAllBook")
    public ResponseEntity<List<BookResponse>> showAllBook(){
        return new ResponseEntity<>(bookService.showAllBook(), HttpStatus.OK);
    }

    @GetMapping("/getBookById/{id}")
    public ResponseEntity<BookResponse> getBookById(@PathVariable("id")Integer id){
        return new ResponseEntity<>(bookService.getBookById(id), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable("id")Integer id){
        return new ResponseEntity<>(bookService.deleteBook(id), HttpStatus.OK);
    }

    @PostMapping("/borrow")
    public ResponseEntity<BorrowedResponse> borrowBooks(@RequestBody BorrowedRequest request){
        return new ResponseEntity<>(bookService.borrowBooks(request), HttpStatus.CREATED);
    }


    @GetMapping("/getBorrowBooks")
    public ResponseEntity<List<ShowBorrowedBooksResponse>> showBorrowBooks(){
        return new ResponseEntity<>(bookService.showBorrowBooks(), HttpStatus.OK);
    }


}