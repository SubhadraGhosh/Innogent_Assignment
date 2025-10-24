package com.example.demo.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import com.example.demo.entity.Book;

public interface BookRepo extends JpaRepository<Book, Integer> {

    @Modifying
    @Query("UPDATE Book b SET b.stock = b.stock - 1 WHERE b.id = :bookId AND b.stock > 0")
    int decreaseBookStock(Integer bookId);

}