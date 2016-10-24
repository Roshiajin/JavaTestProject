package com.epam.jee.repository;

import com.epam.jee.model.Person;

import java.util.List;

public interface PersonRepository {

    List<Person> getPersons();

    Person getPerson(Long id);

    int getNumberOfPersons();

    Long createPerson(String name, String email, String phoneNumber);

    Long createPerson(Person person);

    int deletePerson(Long id);

    void updatePerson(Person person);

}
