package com.example.demo.repository;
import com.example.demo.dto.BookResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import com.example.demo.entity.Book;

import java.util.List;

public interface BookRepo extends JpaRepository<Book, Integer> {

    @Modifying
    @Query("UPDATE Book b SET b.stock = b.stock - 1 WHERE b.id = :bookId AND b.stock > 0")
    int decreaseBookStock(Integer bookId);

   @Query("SELECT  b.title " +
           "FROM Book b WHERE b.author.id = :authorId" )
   List<String> findAllBooksOfThisAuthor(Integer authorId);
}