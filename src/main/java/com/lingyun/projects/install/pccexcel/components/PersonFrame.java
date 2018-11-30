package com.lingyun.projects.install.pccexcel.components;

import com.lingyun.projects.install.pccexcel.domain.person.repo.PersonRepository;
import com.lingyun.projects.install.pccexcel.domain.persongroup.repo.PersonGroupRepository;

import java.awt.*;

public class PersonFrame extends BasicFrame {
    private PersonPanel personPanel;

    public PersonFrame(PersonRepository personRepository,PersonGroupRepository personGroupRepository) {
        setLayout(new BorderLayout());
        personPanel=new PersonPanel(personRepository,personGroupRepository);
        add(personPanel,BorderLayout.CENTER);
    }
}
