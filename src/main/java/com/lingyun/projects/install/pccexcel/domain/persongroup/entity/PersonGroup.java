package com.lingyun.projects.install.pccexcel.domain.persongroup.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
public class PersonGroup {
    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @GeneratedValue(generator ="uuid")
    private String id;
    @Column(unique = true,nullable = false)
    private String groupName;
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

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
