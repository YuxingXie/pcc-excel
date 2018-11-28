package com.lingyun.projects.install.pccexcel.domain.excel.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Excel {
    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @GeneratedValue(generator ="uuid")
    private String id;
    private String path;
    private Date lastOpenDate;
    @Lob
    private byte[] dataJson;
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Date getLastOpenDate() {
        return lastOpenDate;
    }

    public void setLastOpenDate(Date lastOpenDate) {
        this.lastOpenDate = lastOpenDate;
    }

    public byte[] getDataJson() {
        return dataJson;
    }

    public void setDataJson(byte[] dataJson) {
        this.dataJson = dataJson;
    }
}
