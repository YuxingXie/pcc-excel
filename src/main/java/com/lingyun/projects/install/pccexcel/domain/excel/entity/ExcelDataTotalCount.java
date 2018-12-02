package com.lingyun.projects.install.pccexcel.domain.excel.entity;

import com.lingyun.projects.install.pccexcel.domain.person.entity.Person;

public class ExcelDataTotalCount {
    private int totalCount;
    private int loginCount;
    private int viewCount;
    private int praiseCount;
    private int commentCount;
    private int shareCount;
    private int description;
    private Person person;

    public ExcelDataTotalCount(int totalCount, int loginCount, int viewCount, int praiseCount, int commentCount, int shareCount, int description, Person person) {
        this.totalCount = totalCount;
        this.loginCount = loginCount;
        this.viewCount = viewCount;
        this.praiseCount = praiseCount;
        this.commentCount = commentCount;
        this.shareCount = shareCount;
        this.description = description;
        this.person = person;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(int loginCount) {
        this.loginCount = loginCount;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public int getPraiseCount() {
        return praiseCount;
    }

    public void setPraiseCount(int praiseCount) {
        this.praiseCount = praiseCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public int getShareCount() {
        return shareCount;
    }

    public void setShareCount(int shareCount) {
        this.shareCount = shareCount;
    }

    public int getDescription() {
        return description;
    }

    public void setDescription(int description) {
        this.description = description;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
