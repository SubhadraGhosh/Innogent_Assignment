package com.example.demo.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.Member;
import com.example.demo.dto.ShowBorrowedBooksResponse;

public interface MemberRepo extends JpaRepository<Member, Integer> {

    @Query("SELECT new com.example.demo.dto.ShowBorrowedBooksResponse(m.name, m.email, b.title,b.description,b.price,b.id) " +
            "FROM Member m JOIN m.books b")
    List<ShowBorrowedBooksResponse> findAllBorrowedBooks();
}