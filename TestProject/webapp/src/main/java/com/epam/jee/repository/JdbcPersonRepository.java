package com.epam.jee.repository;

import com.epam.jee.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class JdbcPersonRepository implements PersonRepository {

    private JdbcTemplate template;
    private static long nextId = 4;

    @Autowired
    public JdbcPersonRepository(DataSource dataSource) {
        template = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Person> getPersons() {
        String sqlTxt = "select * from person";
        return template.query(sqlTxt, new PersonMapper());
    }

    @Override
    public Person getPerson(Long id) {
        String sqlTxt = "select * from person where id = ?";
        return template.queryForObject(sqlTxt, new PersonMapper(), id);
    }

    @Override
    public int getNumberOfPersons() {
        String sqlTxt = "select count(*) from person";
        return template.queryForObject(sqlTxt, Integer.class);
    }

    @Override
    public Long createPerson(String name, String email, String phoneNumber) {
        String sqlTxt = "insert into person (id, name, email, phoneNumber) values (?,?,?,?)";
        long id = nextId++;
        int cp = template.update(sqlTxt, id, name, email, phoneNumber);
        return id;
    }

    @Override
    public Long createPerson(Person person) {
        return createPerson(person.getName(), person.getEmail(), person.getPhoneNumber());
    }

    @Override
    public int deletePerson(Long id) {
        String sqlTxt = "delete from person where id =?";
        return template.update(sqlTxt, id);
    }

    @Override
    public void updatePerson(Person person) {
        String sqlTxt = "update person set name = ?, email = ?, phoneNumber = ? where id = ?";
        template.update(sqlTxt, person.getName(), person.getEmail(), person.getPhoneNumber(), person.getId());
    }

    private class PersonMapper implements RowMapper<Person> {
        @Override
        public Person mapRow(ResultSet resultSet, int i) throws SQLException {
            return new Person(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getString("email"),
                    resultSet.getString("phoneNumber"));
        }
    }
}
