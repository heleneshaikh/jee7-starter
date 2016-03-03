package com.realdolmen.course.persistence;

import com.realdolmen.course.domain.Book;
import com.realdolmen.course.persistence.DataSetPersistenceTest;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javax.persistence.PersistenceException;

public class BookPersistenceTest extends DataSetPersistenceTest {
    @Rule
    public ExpectedException expector = ExpectedException.none();

    @Test
    public void bookCanBePersisted() throws Exception {
        Book book = new Book("Animal Farm", "George Orwell", Book.Genre.thriller);
        entityManager().persist(book); //Direct met EM werken
        assertNotNull(book.getId());
    }

    @Test(expected = PersistenceException.class)
    public void bookCanNotBePersistedWithoutTitle() throws Exception {
        Book book = new Book(null, "John Doe", Book.Genre.thriller);
        entityManager().persist(book);
    }

    @Test
    public void bookCanBeRetrievedById() throws Exception {
        assertEquals("Nineteen Eighty Four", entityManager().find(Book.class, 1000).getTitle());
    }
}
