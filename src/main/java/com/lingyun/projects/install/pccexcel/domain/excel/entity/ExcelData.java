package com.lingyun.projects.install.pccexcel.domain.excel.entity;

import com.lingyun.common.support.data.Excelable;
import com.lingyun.projects.install.pccexcel.domain.person.entity.Person;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
public class ExcelData implements Excelable {
    public static final String[] columnNames=new String[]{"排名","姓名","登录次数","浏览次数","点赞次数","评论次数","分享次数","总次数"};
    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @GeneratedValue(generator ="uuid")
    private String id;
    private int sheetIndex;
    private String sheetName;//excel工作表名称，一个excel文件包含1到多个sheet
    @Column(name = "orderNumber")//"order"是sql 关键字
    private int order;//在excel文件中的排序，one-based
    @ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH},fetch = FetchType.EAGER)
    @JoinColumn(name = "personId")
    private Person person;
    @ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH},fetch = FetchType.EAGER)
    @JoinColumn(name = "excelId")
    private Excel excel;
    private String description;//备注，同名的时候加个备注说明人员被关联到了谁

    //excel columns below
    private Integer loginCount;//登录
    private Integer viewCount;//浏览
    private Integer praiseCount;//点赞
    private Integer commentCount;//评论
    private Integer shareCount;//分享
    @Transient
    private Integer totalCount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public Person getPerson() {
        return person;
    }

    public int getSheetIndex() {
        return sheetIndex;
    }

    public void setSheetIndex(int sheetIndex) {
        this.sheetIndex = sheetIndex;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Excel getExcel() {
        return excel;
    }

    public void setExcel(Excel excel) {
        this.excel = excel;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(Integer loginCount) {
        this.loginCount = loginCount;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public Integer getPraiseCount() {
        return praiseCount;
    }

    public void setPraiseCount(Integer praiseCount) {
        this.praiseCount = praiseCount;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public Integer getShareCount() {
        return shareCount;
    }

    public void setShareCount(Integer shareCount) {
        this.shareCount = shareCount;
    }

    public Integer getTotalCount() {
        if (totalCount!=null) return totalCount;
        return (loginCount==null?0:loginCount)
                +(shareCount==null?0:shareCount)
                +(commentCount==null?0:commentCount)
                +(praiseCount==null?0:praiseCount)
                +(viewCount==null?0:viewCount);
    }


    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    @Override
    public Map<String, Object> toExcelRow() {
        Map<String, Object> map=new HashMap<>();
//        {"排名","姓名","登录次数","浏览次数","点赞次数","评论次数","分享次数","总次数"};

        map.put(columnNames[0],null);
        map.put(columnNames[1],person.getName());
        map.put(columnNames[2],loginCount);
        map.put(columnNames[3],viewCount);
        map.put(columnNames[4],praiseCount);
        map.put(columnNames[5],commentCount);
        map.put(columnNames[6],shareCount);
        map.put(columnNames[7],getTotalCount());
        return map;
    }
}
