package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.AuthorRequest;
import com.example.demo.dto.AuthorResponse;

public interface AuthorService {

    public AuthorResponse addAuthor(AuthorRequest request);
    public List<AuthorResponse> showAllAuthor();

    public AuthorResponse getAuthorById(Integer id);
    public AuthorResponse updateAuthor(Integer id, AuthorRequest request);
    public String deleteAuthor(Integer id);
}