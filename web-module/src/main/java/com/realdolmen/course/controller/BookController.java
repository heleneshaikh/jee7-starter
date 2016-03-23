package com.realdolmen.course.controller;

import com.realdolmen.course.domain.Book;
import com.realdolmen.course.persistence.BookRepository;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@RequestScoped //for the duration of a single request
public class BookController {
    @Inject
    BookRepository repository;

    public List<Book> getAllBooks() {
        return repository.findAll();
    }

    public void remove(int bookId) {
        repository.remove(bookId);
    }

    public Book findById(Integer id) {
        return repository.findById(id);
    }
}
