package com.lingyun.projects.install.pccexcel.components.tables.models;

import com.lingyun.common.support.util.date.DateTimeUtil;
import com.lingyun.projects.install.pccexcel.domain.persongroup.entity.PersonGroup;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

public class PersonGroupTableModel extends DefaultTableModel {
    private List<PersonGroup> personGroups;
    public boolean isCellEditable(int row, int column) {
        return column != 0 && column!=3;
    }

    public Object getValueAt(int row, int column) {
        Object superObject =super.getValueAt(row,column);
        Object value;
        PersonGroup personGroup=this.personGroups.get(row);
        if(personGroup==null){
            personGroup=new PersonGroup();
            this.personGroups.add(personGroup);
        }
        if (column==0) {
            value = personGroup.getId()==null?superObject:personGroup.getId();
            if(personGroup.getId()==null&&value!=null){
                personGroup.setId(value.toString());
            }
        }else if (column==1){
             value = personGroup.getGroupName()==null?superObject:personGroup.getGroupName();
            if(personGroup.getGroupName()==null&&value!=null){
                personGroup.setGroupName(value.toString());
            }
        }else if (column==2) {
            value = personGroup.getDescription()==null?superObject:personGroup.getDescription();
            if(personGroup.getDescription()==null&&value!=null){
                personGroup.setDescription(value.toString());
            }
        }else if (column==3) {
            value = personGroup.getCreateDate()==null?superObject:DateTimeUtil.DateRepresentation.toString(personGroup.getCreateDate(),DateTimeUtil.DateFormatString.yyyy_MM_ddHH$mm$ss);
            if(personGroup.getCreateDate()==null&&value!=null){
                personGroup.setCreateDate(DateTimeUtil.DateConvert.convertStringToDateTime(DateTimeUtil.DateFormatString.yyyy_MM_ddHH$mm$ss,value.toString()));
            }
        }
        else {
            value= superObject;
        }
        return value;
    }

    public PersonGroupTableModel(Object[][] data, Object[] columnNames) {
        super(data, columnNames);
        personGroups=new ArrayList<>();
        for(int i=0;i<data.length;i++){
            PersonGroup personGroup=new PersonGroup();
            if(data[i][0]!=null) personGroup.setId(data[i][0].toString());
            if(data[i][1]!=null) personGroup.setGroupName(data[i][1].toString());
            if(data[i][2]!=null) personGroup.setDescription(data[i][2].toString());
            if(data[i][3]!=null) personGroup.setCreateDate(DateTimeUtil.DateConvert.convertStringToDateTime(DateTimeUtil.DateFormatString.yyyy_MM_ddHH$mm$ss,data[i][3].toString()));
            personGroups.add(personGroup);
        }
    }

    public PersonGroupTableModel(List<PersonGroup> personGroups) {

//        Object[] columnNames=new Object[]{"分组id(不可编辑)","分组名称","描述"};
//        Object[][] rowData=new Object[personGroups.size()][columnNames.length];
//        for(int i=0;i<personGroups.size();i++){
//            PersonGroup personGroup=personGroups.get(i);
//            rowData[i][0]=personGroup.getId();
//            rowData[i][1]=personGroup.getGroupName();
//            rowData[i][2]=personGroup.getDescription();
//        }

        this.personGroups = personGroups;

    }



    public List<PersonGroup> getPersonGroups() {
        return personGroups;
    }

    public void setPersonGroups(List<PersonGroup> personGroups) {
        this.personGroups = personGroups;
    }

    @Override
    public void addRow(Object[] data) {
        super.addRow(data);
        PersonGroup personGroup=new PersonGroup();
        if(data[0]!=null) personGroup.setId(data[0].toString());
        if(data[1]!=null) personGroup.setGroupName(data[1].toString());
        if(data[2]!=null) personGroup.setDescription(data[2].toString());
        if(data[3]!=null) personGroup.setCreateDate(DateTimeUtil.DateConvert.convertStringToDateTime(DateTimeUtil.DateFormatString.yyyy_MM_ddHH$mm$ss,data[3].toString()));
        this.personGroups.add(personGroup);
    }

    @Override
    public void removeRow(int row) {
        super.removeRow(row);
        personGroups.remove(row);
    }
}
