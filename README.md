[![Build Status](https://travis-ci.org/kvanrobbroeck/jee7-starter.svg?branch=master)](https://travis-ci.org/kvanrobbroeck/jee7-starter)

This project serves as a demo/template for JEE6 and JEE7 development.

Demonstrates the following:
    - JEE7
    - EJB
    - CDI
    - JSF
    - JPA
    - JMS
    - Remote JNDI using unit tests (run them with -Dintegration or they will be skipped)

Verified for use on Wildfly 9.0.1.Final. Apply the "wildfly-9.0.1.Final-diff.zip" file over a clean wildfly 9.0.1 installation to set things up correctly.

Requires:
    - Maven
    - MySQL server (schema "test")