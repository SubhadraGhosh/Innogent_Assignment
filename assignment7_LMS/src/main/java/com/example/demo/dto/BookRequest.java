package com.example.demo.dto;
import java.util.List;
import lombok.Data;

@Data
public class BookRequest {

    private Integer bookPrice;
    private Integer bookStock;
    private String description;
    private String title;
    private Integer authorId;
}