package com.lingyun.projects.install.pccexcel.domain.excel.entity;

import com.lingyun.common.support.util.clazz.BeanUtil;
import com.lingyun.common.support.util.file.OLE2OfficeExcelUtils;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.SortedMap;

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

    public SortedMap<String, List<List<Object>>> toSortedMap(){
        SortedMap<String, List<List<Object>>> sortedMap =null;
        try {
            sortedMap = BeanUtil.jsonToJava(new String(dataJson,"UTF-8"),SortedMap.class);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
//        OLE2OfficeExcelUtils.printSortedMap(sortedMap);
        return sortedMap;
    }

    @Override
    public String toString() {
        if (!path.contains(File.separator)) return path;
        if (path.endsWith(File.separator)) return path;
        return path.substring(path.lastIndexOf(File.separator)+1);
    }
}
