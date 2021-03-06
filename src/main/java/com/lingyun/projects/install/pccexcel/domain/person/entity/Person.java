package com.lingyun.projects.install.pccexcel.domain.person.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lingyun.projects.install.pccexcel.domain.persongroup.entity.PersonGroup;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Person {
    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @GeneratedValue(generator ="uuid")
    private String id;

    @ManyToOne(cascade={CascadeType.REFRESH},fetch = FetchType.EAGER)
    @JoinColumn(name = "personGroupId")
    private PersonGroup personGroup;
    private String name;
    private String description;
    @Column(updatable = false)
    private Date createDate;
    @PrePersist
    void preInsert() {
        if (this.createDate == null)
            this.createDate = new Date();
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PersonGroup getPersonGroup() {
        return personGroup;
    }

    public void setPersonGroup(PersonGroup personGroup) {
        this.personGroup = personGroup;
    }
}
