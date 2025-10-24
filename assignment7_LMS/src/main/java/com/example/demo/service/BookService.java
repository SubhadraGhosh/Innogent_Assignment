package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.BookRequest;
import com.example.demo.dto.BorrowedRequest;
import com.example.demo.dto.ShowBorrowedBooksResponse;
import com.example.demo.dto.BookResponse;
import com.example.demo.dto.BorrowedResponse;

public interface BookService {

    public BookResponse addBook(BookRequest request);
    public List<BookResponse> showAllBook();
    public BookResponse getBookById(Integer id);
    public String deleteBook(Integer id);
    public BorrowedResponse borrowBooks(BorrowedRequest request);
    public List<ShowBorrowedBooksResponse> showBorrowBooks();

}