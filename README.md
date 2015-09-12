JEE7 Starter
============

[![Build Status](https://travis-ci.org/kvanrobbroeck/jee7-starter.svg?branch=master)](https://travis-ci.org/kvanrobbroeck/jee7-starter)

This project serves as a demo/template for JEE6 and JEE7 development. It is used as a starting point for the following
RealDolmen courses:
    * Developing Java Enterprise Applications with JEE 6
    * Building Enterprise Applications with JEE 7
    * Enterprise JavaBeans (EJB) 3.1
    * Java Enterprise Component Development with JPA 2 and EJB 3.1
    * Java Persistence API (JPA) 2

Demonstrated features
---------------------
    * JEE7
    * EJB
    * CDI
    * JSF
    * JPA
    * JMS
    * Remote JNDI using unit tests (run them with -Dintegration or they will be skipped)

Platform
--------
Verified for use on Wildfly 9.0.1.Final. Apply the "wildfly-9.0.1.Final-diff.zip" file over a clean wildfly 9.0.1 installation to set things up correctly.

Prerequisistes
--------------
    * Maven
    * MySQL server (schema "test")