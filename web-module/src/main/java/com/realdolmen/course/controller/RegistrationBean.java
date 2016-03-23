package com.realdolmen.course.controller;

import com.realdolmen.course.domain.Book;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by heleneshaikh on 03/03/16.
 */

@Named
@RequestScoped
public class RegistrationBean implements Serializable {
    @NotNull
    private URL url;
    @Length(min = 3, max = 40)
    @NotNull
    private String firstName;
    @Length(min = 3, max = 40)
    @NotNull
    private String lastName;
    @Past
    @NotNull
    private Date dateOfBirth;
    private Gender gender;
    private String title;
    private ServiceLevel serviceLevel;
    private String phoneNumber;
    private String accountNumber;
    // @Pattern(regexp = ".*@.*\\..*")
    @Email
    private String email;
    private List<String> elements = new ArrayList<>();


    public String register() {
        System.out.println("Submitted!");
        return "books.xhtml";
    }

    public enum ServiceLevel {
        BASIC, MEDIUM, HIGH;
    }


    public enum Gender {
        MALE, FEMALE;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getElements() {
        return elements;
    }

    public void setElements(List<String> elements) {
        this.elements = elements;
    }

    public ServiceLevel getServiceLevel() {
        return serviceLevel;
    }

    public void setServiceLevel(ServiceLevel serviceLevel) {
        this.serviceLevel = serviceLevel;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @PostConstruct
    public void init() {
        this.firstName = "Helene";
        this.lastName = "Shaikh";
        this.dateOfBirth = new Date();
        this.title = "Into the Wild";
        this.email = "helene.shaikh@gmail.com";
        elements.add("First name: " + firstName);
        elements.add("Last name: " + lastName);
        elements.add("Book title: " + title);
    }

    public void validateEmail(FacesContext context, UIComponent toValidate, Object value) throws ValidatorException {
        String eMail = (String) value;
        if (!email.contains("@")) {
            FacesMessage message = new FacesMessage("invalid email address");
            throw new ValidatorException(message);
        }
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }
}
