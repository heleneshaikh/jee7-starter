package com.realdolmen.course.persistence;


import com.realdolmen.course.domain.*;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceException;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.time.format.DateTimeFormatter;

/**
 * Created by SDOAX36 on 9/09/2015.
 */
public class PassengerPersistenceTest extends DataSetPersistenceTest {

    @Rule
    public ExpectedException expector = ExpectedException.none();

    @Test
    public void passengerCanBePersisted() throws Exception {
        Passenger p = new Passenger("02123321", "john", "Doe", 15, "15/02/1990", PassengerType.ECONOMY, new Adress("Sesamstraat", "", "Sprookjesbos", "99955", "Holland"));
        entityManager().persist(p);
        assertNotNull(p.getId());
    }

    @Test(expected = PersistenceException.class)
    public void passengerCanNotBePersistedWithoutSsn() throws Exception {
        Passenger p = new Passenger(null, "John","Smith",15,"15/02/1990", PassengerType.ECONOMY,new Adress("Sesamstraat", "", "Sprookjesbos", "99955", "Holland"));
        entityManager().persist(p);
        entityManager().flush();
    }
    @Test(expected = PersistenceException.class)
    public void passengerCanNotBePersistedWithoutFirstName() throws Exception{
        Passenger p = new Passenger("122313213",null,"Smithers",11,"15/02/1990", PassengerType.ECONOMY,new Adress("Sesamstraat", "", "Sprookjesbos", "99955", "Holland"));
        entityManager().persist(p);
        entityManager().flush();
    }
    @Test(expected = PersistenceException.class)
    public void passengerCanNotBePersistedWithoutLastName() throws Exception{
        Passenger p = new Passenger("122313213","jan",null,11,"15/02/1990", PassengerType.ECONOMY,new Adress("Sesamstraat", "", "Sprookjesbos", "99955", "Holland"));
        entityManager().persist(p);
        entityManager().flush();
    }

    @Test
    public void passengerCanBeRetrievedById() throws Exception {

        assertEquals("87010835575", entityManager().find(Passenger.class, 1000L).getSsn());

    }
    @Test
    public void canAddCreditCardToPassenger() throws Exception{
        Passenger p = entityManager().find(Passenger.class, 1000L);

        p.addCreditCardToList(new CreditCard("121232131", "21/28", 15, CreditCardType.AMEX));
        int count = p.getCreditCardCount();
        entityManager().persist(p);
        entityManager().flush();
        Passenger p2 = entityManager().find(Passenger.class, 1000L);

        assertEquals(count, p2.getCreditCardCount());
    }
    @Test
    public void canAddPreferenceToPassenger() throws Exception{
        Passenger p = entityManager().find(Passenger.class, 1000L);
        p.addPreference("a pref");
        int count = p.getPrefListCount();
        p.setAantalPrefs(count);
        entityManager().persist(p);
        entityManager().flush();
        Passenger p2 = entityManager().find(Passenger.class,1000L);
        int count2 = p2.getPrefListCount();
;
        assertEquals(count,count2);

    }

    @Test
    public void canUpdatePassenger()throws Exception{

        Passenger p = entityManager().find(Passenger.class,1000L);
        p.setFirstName("Jantje");
        entityManager().merge(p);
        entityManager().flush();
        assertEquals("Jantje", entityManager().find(Passenger.class, 1000L).getFirstName());
    }

    @Test(expected = EntityNotFoundException.class)
    public void canDeletePassenger() throws Exception{
        Passenger p = entityManager().getReference(Passenger.class, 1000L);
        entityManager().remove(p);
        entityManager().flush();
        assertNull("Passenger shoud be null", entityManager().getReference(Passenger.class, 1000L));
    }
    @Test
    public void passengerAgeCanBeCalculated() throws Exception{
        LocalDate birthDate = LocalDate.of(1990, Month.FEBRUARY, 15);
        Passenger p = new Passenger("122313213","jan","De Boel",11,birthDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), PassengerType.ECONOMY,new Adress("Sesamstraat", "", "Sprookjesbos", "99955", "Holland"));

        LocalDate now = LocalDate.now();
        int age = Period.between(birthDate, now).getYears();
        assertEquals(age, p.getAge());
    }
    @Test
    public void refreshPassengerData() throws Exception{
        Passenger p = entityManager().find(Passenger.class,1000L);
        assertEquals(p.getFirstName(),"Steven");
        p.setFirstName("jon");
        entityManager().refresh(p);
        assertEquals(p.getFirstName(),"Steven");
    }

}