package com.example.demo.dto;
import lombok.Data;

@Data
public class BorrowedResponse {

    private Integer bookId;
    private Integer memberId;
    private String bookTitle;
    private String status;
    private Integer bookStock;
}