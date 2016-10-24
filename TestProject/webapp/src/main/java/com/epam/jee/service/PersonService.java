package com.epam.jee.service;

import com.epam.jee.model.Person;
import com.epam.jee.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import javax.faces.context.FacesContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.faces.application.FacesMessage;

@Service
@Transactional
public class PersonService {

    @Autowired
    private PersonRepository repository;

    private Person newPerson;

    @Autowired
    public void initNewPerson () { newPerson = new Person();}

    public void register() {

        FacesMessage msg;

        try {
            repository.createPerson(newPerson);
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registered!", "Registration successful");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            initNewPerson();
        } catch (Exception e) {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Registration error!", "Registration unsuccessful");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }

    }

    public Person getNewPerson() {
        return newPerson;
    }

    public void setNewPerson(Person newPerson) {
        this.newPerson = newPerson;
    }
}
