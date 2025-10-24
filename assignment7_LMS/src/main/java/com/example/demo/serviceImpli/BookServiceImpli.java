package com.example.demo.serviceImpli;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.BookDao;
import com.example.demo.dto.BookRequest;
import com.example.demo.dto.BorrowedRequest;
import com.example.demo.dto.ShowBorrowedBooksResponse;
import com.example.demo.dto.BookResponse;
import com.example.demo.dto.BorrowedResponse;
import com.example.demo.service.BookService;

@Service
public class BookServiceImpli implements BookService {

    @Autowired
    private BookDao dao;

    @Override
    public BookResponse addBook(BookRequest request) {
        return dao.addBook(request);
    }

    @Override
    public List<BookResponse> showAllBook() {
        return dao.showAllBook();
    }

    @Override
    public BookResponse getBookById(Integer id) {
        return dao.getBookById(id);
    }

    @Override
    public String deleteBook(Integer id) {
        return dao.deleteBook(id);
    }

    @Override
    public BorrowedResponse borrowBooks(BorrowedRequest request) {
        return dao.borrowBooks(request);
    }

    @Override
    public List<ShowBorrowedBooksResponse> showBorrowBooks() {
        return dao.showBorrowBooks();
    }



}