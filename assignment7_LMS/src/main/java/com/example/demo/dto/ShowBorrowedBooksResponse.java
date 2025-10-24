package com.example.demo.dto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ShowBorrowedBooksResponse {

    private String memberName;
    private String memberEmail;
    private String bookTitle;
    private String bookDescription;
    private Integer bookPrice;
    private Integer bookId;
}