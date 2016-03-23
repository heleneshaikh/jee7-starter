package com.realdolmen.course.persistence;

import com.realdolmen.course.domain.Book;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
@LocalBean
public class BookRepository implements RemoteBookRepository {
    @PersistenceContext
    EntityManager entityManager;

    public List<Book> findAll() {
        return entityManager.createQuery("select b from Book b", Book.class).getResultList(); //not an error, jpql plugin download
    }

    public Book findById(Integer id) {
        return entityManager.find(Book.class, id);
    }

    public void remove(int id) {
        entityManager.remove(entityManager.getReference(Book.class, id));
    }


}
