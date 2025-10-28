
package com.example.demo.dto;
import lombok.Data;

import java.awt.print.Book;
import java.util.List;

@Data
public class AuthorResponse {

    private Integer id;
    private String name;
    private String email;
    private List<String> books;
}
