package com.example.demo.serviceImpli;
import java.util.List;

import com.example.demo.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.dao.AuthorDao;
import com.example.demo.dto.AuthorRequest;
import com.example.demo.dto.AuthorResponse;
import com.example.demo.service.AuthorService;

@Service
public class AuthorServiceImpli implements AuthorService {

    @Autowired
    private AuthorDao dao;

    @Override
    public AuthorResponse addAuthor(AuthorRequest request) {
        return dao.addAuthor(request);
    }

    @Override
    public List<AuthorResponse> showAllAuthor() {
        return dao.showAllAuthor();
    }

    @Override
    public AuthorResponse getAuthorById(Integer id) {
        return dao.getAuthorById(id);
    }

    @Override
    public AuthorResponse updateAuthor(Integer id, AuthorRequest request) {
        // TODO Auto-generated method stub
        return dao.updateAuthor(id,request);
    }

    @Override
    public String deleteAuthor(Integer id) {
        // TODO Auto-generated method stub
        return dao.deleteAuthor(id);
    }

}