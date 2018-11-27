package com.lingyun.projects.install.pccexcel.domain.entity;

import com.lingyun.common.support.util.file.ExcelUtils;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.File;

@Entity
public class Excel {
    @Id
    private String id;
    private String path;
    private boolean current;

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

    public boolean isCurrent() {
        return current;
    }

    public void setCurrent(boolean current) {
        this.current = current;
    }

}
