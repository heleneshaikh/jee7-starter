package com.realdolmen.course.persistence;

import com.realdolmen.course.domain.Book;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.criteria.Order;

@Stateless(name = "KevinsBookService")
public class BookService {
    @EJB
    private BookRepository bookRepository;

    @EJB
    private OrderRepository orderRepository;

    public void sellBook(String isbn) {

    }
}
