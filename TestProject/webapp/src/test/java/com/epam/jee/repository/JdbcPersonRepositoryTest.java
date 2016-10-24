package com.epam.jee.repository;

import com.epam.jee.config.AppConfig;
import com.epam.jee.model.Person;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
@Transactional
@ActiveProfiles({"test"})
public class JdbcPersonRepositoryTest {

    @Autowired
    private PersonRepository repository;

    @Test
    public void testGetPersons() throws Exception {
        List<Person> persons = repository.getPersons();
        assertEquals(3,persons.size());
    }

    @Test
    public void testGetPerson() throws Exception {
        Person person = repository.getPerson(1L);
        assertEquals(new Long(1), person.getId());
    }

    @Test
    public void testGetNumberOfPersons() throws Exception {
        assertEquals( 3, repository.getNumberOfPersons());
    }

    @Test
    public void testCreatePerson() throws Exception {
        Long id = repository.createPerson("Eva","eva@webapp.com","12345678904");
        assertNotNull(id);

        Person person = repository.getPerson(id);
        assertEquals(id, person.getId());
        assertEquals("Eva",person.getName());
        assertEquals("eva@webapp.com",person.getEmail());
        assertEquals("12345678904",person.getPhoneNumber());
    }

    @Test
    public void testUpdatePerson() throws Exception {
        Person person = repository.getPerson(1L);
        person.setName("John");
        person.setEmail("john@webapp.com");
        person.setPhoneNumber("98765432101");
        repository.updatePerson(person);

        Person againPerson = repository.getPerson(1L);
        assertEquals("John", againPerson.getName());
        assertEquals("john@webapp.com", againPerson.getEmail());
        assertEquals("98765432101", againPerson.getPhoneNumber());

    }

    @Test
    public void testDeletePerson() throws Exception {
        for (Person person : repository.getPersons()) {
            repository.deletePerson(person.getId());
        }
        assertEquals(0, repository.getNumberOfPersons());
    }

}
