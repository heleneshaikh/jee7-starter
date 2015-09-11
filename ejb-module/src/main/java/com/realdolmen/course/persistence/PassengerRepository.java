package com.realdolmen.course.persistence;

import com.realdolmen.course.domain.Book;
import com.realdolmen.course.domain.Passenger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by SDOAX36 on 9/09/2015.
 */
public class PassengerRepository implements RemotePassengerRepository{

    @PersistenceContext
    EntityManager em;
    @Override
    public List<Passenger> findAll() {

        List<Passenger> passengers = em.createQuery("select p from Passenger p", Passenger.class).getResultList();
        return passengers;
    }

    //public to remove
}
