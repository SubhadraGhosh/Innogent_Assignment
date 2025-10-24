package com.example.demo.daoImpli;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.example.demo.dao.AuthorDao;
import com.example.demo.entity.Author;
import com.example.demo.repository.AuthorRepo;
import com.example.demo.dto.AuthorRequest;
import com.example.demo.dto.AuthorResponse;

@Component
public class AuthorDaoImpli implements AuthorDao {

    @Autowired
    private AuthorRepo repo;

    @Override
    public AuthorResponse addAuthor(AuthorRequest request) {
        Author author = new Author();
        author.setName(request.getName());
        author.setEmail(request.getEmail());

        repo.save(author);
        AuthorResponse response = new AuthorResponse();
        response.setName(author.getName());
        response.setEmail(author.getEmail());
        return response;
    }

    @Override
    public List<AuthorResponse> showAllAuthor() {
        List<Author> author = repo.findAll();
        List<AuthorResponse> response = new ArrayList<>();

        for(Author auth : author) {
            AuthorResponse res = new AuthorResponse();
            res.setName(auth.getName());
            res.setEmail(auth.getEmail());
            res.setId(auth.getId());

            response.add(res);
        }
        return response;
    }

    @Override
    public AuthorResponse getAuthorById(Integer id) {
        Author author = repo.findById(id).get();
        AuthorResponse response = new AuthorResponse();
        response.setEmail(author.getName());
        response.setName(author.getEmail());
        response.setId(author.getId());
        return response;
    }

    @Override
    public AuthorResponse updateAuthor(Integer id, AuthorRequest request) {
        Author authorId = repo.findById(id).get();
        AuthorResponse response = new AuthorResponse();
        if(authorId!=null) {

            authorId.setName(request.getName());
            authorId.setEmail(request.getEmail());

            repo.save(authorId);
            response.setEmail(request.getEmail());
            response.setName(request.getName());
        }
        return response;
    }

    @Override
    public String deleteAuthor(Integer id) {
        boolean existsById = repo.existsById(id);
        if(existsById) {
            repo.deleteById(id);
        }
        return "deleted successfully";
    }

}