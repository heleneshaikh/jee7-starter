package com.realdolmen.course.persistence;

import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class BookOnlineWebsiteServiceConversationBean {
    @EJB
    private BookService bookService;
}
