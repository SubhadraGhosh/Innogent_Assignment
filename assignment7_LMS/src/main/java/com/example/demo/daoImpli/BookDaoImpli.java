package com.example.demo.daoImpli;
import java.util.ArrayList;
import java.util.List;

import com.example.demo.dao.BookDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.example.demo.dao.BookDao;
import com.example.demo.entity.Author;
import com.example.demo.entity.Book;
import com.example.demo.entity.Member;
import com.example.demo.repository.AuthorRepo;
import com.example.demo.repository.BookRepo;
import com.example.demo.repository.MemberRepo;
import com.example.demo.dto.BookRequest;
import com.example.demo.dto.BorrowedRequest;
import com.example.demo.dto.ShowBorrowedBooksResponse;
import com.example.demo.dto.BookResponse;
import com.example.demo.dto.BorrowedResponse;
import jakarta.transaction.Transactional;

@Component
public class BookDaoImpli implements BookDao {

    @Autowired
    private BookRepo repo;

    @Autowired
    private AuthorRepo authorRepo;

    @Autowired
    private MemberRepo memberRepo;

    @Override
    public BookResponse addBook(BookRequest request) {
        Book book = new Book();


        book.setTitle(request.getTitle());
        book.setDescription(request.getDescription());
        book.setPrice(request.getBookPrice());
        book.setStock(request.getBookStock());

        Integer authorId = request.getAuthorId();

            Author authorDetails = authorRepo.findById(authorId).get();
            if(authorDetails!=null) {
                book.setAuthor(authorDetails);
            }else {
                System.out.println("author not found");
            }
            book.setAuthor(authorDetails);


        repo.save(book);
        BookResponse response = new BookResponse();
        response.setBookTitle(book.getTitle());
        response.setBookDesc(book.getDescription());
        response.setBookPrice(book.getPrice());
        response.setBookStock(book.getStock());

        response.setAuthorName(book.getAuthor().getName());
        return response;
    }

    @Override
    public List<BookResponse> showAllBook() {
        List<Book> books = repo.findAll();
        List<BookResponse> response = new ArrayList<>();
        for(Book book : books) {
            BookResponse res = new BookResponse();
            res.setBookDesc(book.getDescription());
            res.setBookId(book.getId());
            res.setBookPrice(book.getPrice());
            res.setBookStock(book.getStock());
            res.setBookTitle(book.getTitle());


            res.setAuthorName(book.getAuthor().getName());

            response.add(res);
        }
        return response;
    }

    @Override
    public BookResponse getBookById(Integer id) {
        BookResponse response = new BookResponse();
        Book book = repo.findById(id).get();
        response.setBookId(book.getId());
        response.setBookDesc(book.getDescription());
        response.setBookPrice(book.getPrice());
        response.setBookStock(book.getStock());
        response.setBookTitle(book.getTitle());

        response.setAuthorName(book.getAuthor().getName());
        return response;
    }

    @Override
    public String deleteBook(Integer id) {
        Book bookId = repo.findById(id).get();
        repo.deleteById(bookId.getId());
        return "Deleted";

    }

    @Override
    @Transactional
    public BorrowedResponse borrowBooks(BorrowedRequest request) {
        BorrowedResponse response = new BorrowedResponse();
        Book bookId = repo.findById(request.getBookId()).get();
        Member member = memberRepo.findById(request.getMemberId()).get();
        int update = repo.decreaseBookStock(request.getBookId());
        response.setBookId(request.getBookId());
        response.setMemberId(request.getMemberId());
        response.setBookTitle(bookId.getTitle());
        response.setBookStock(update);
        response.setStatus("Borrowed SuccessFully");
        member.getBooks().add(bookId);
        bookId.getMembers().add(member);
        return response;
    }

    @Override
    public List<ShowBorrowedBooksResponse> showBorrowBooks() {
        return memberRepo.findAllBorrowedBooks();

    }

}