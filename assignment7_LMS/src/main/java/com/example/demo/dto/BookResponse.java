package com.example.demo.dto;
import java.util.List;
import lombok.Data;

@Data
public class BookResponse {

    private Integer bookId;
    private String bookTitle;
    private Integer bookPrice;
    private String bookDesc;
    private Integer bookStock;
    private String authorName;
}