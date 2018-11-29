package com.lingyun.projects.install.pccexcel.components;

import com.lingyun.projects.install.pccexcel.domain.person.repo.PersonRepository;

import java.awt.*;

public class PersonFrame extends BasicFrame {
    private PersonPanel personPanel;

    public PersonFrame(PersonRepository personRepository) {
        setLayout(new BorderLayout());
        personPanel=new PersonPanel(personRepository);
        add(personPanel,BorderLayout.CENTER);
    }
}
