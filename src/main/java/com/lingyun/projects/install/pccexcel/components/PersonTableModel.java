package com.lingyun.projects.install.pccexcel.components;

import com.lingyun.common.support.util.date.DateTimeUtil;
import com.lingyun.projects.install.pccexcel.domain.person.entity.Person;
import com.lingyun.projects.install.pccexcel.domain.persongroup.entity.PersonGroup;
import com.lingyun.projects.install.pccexcel.support.ComponentsDrawTools;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

public class PersonTableModel extends DefaultTableModel {
    private List<Person> persons;
    public PersonTableModel(Object[][] data, Object[] columnNames) {
        super(data, columnNames);
        this.persons=new ArrayList<>();//重建一个新的list，以便和表格保持同步，需要获得table中编辑过的数据时调用getPersons()
        for(int i=0;i<data.length;i++){
            Person person=new Person();
            PersonGroup personGroup=new PersonGroup();
            if(data[i][0]!=null) person.setId(data[i][0].toString());
            if(data[i][1]!=null) person.setName(data[i][1].toString());
            if(data[i][2]!=null) person.setCreateDate(DateTimeUtil.DateConvert.convertStringToDateTime(DateTimeUtil.DateFormatString.yyyy_MM_ddHH$mm$ss,data[i][2].toString()));
            if(data[i][3]!=null) person.setDescription(data[i][3].toString());
            if(data[i][4]!=null) personGroup.setId(data[i][4].toString());//隐藏列PersonGroup.id
            if(data[i][5]!=null) personGroup.setGroupName(data[i][5].toString());
            person.setPersonGroup(personGroup);
            this.persons.add(person);

        }

    }

    @Override
    public void addRow(Object[] data) {
        super.addRow(data);
        Person person=new Person();
        PersonGroup personGroup=new PersonGroup();
        if(data[0]!=null) person.setId(data[0].toString());
        if(data[1]!=null) person.setName(data[1].toString());
        if(data[2]!=null) person.setCreateDate(DateTimeUtil.DateConvert.convertStringToDateTime(DateTimeUtil.DateFormatString.yyyy_MM_ddHH$mm$ss,data[2].toString()));
        if(data[3]!=null) person.setDescription(data[3].toString());
        if(data[4]!=null) personGroup.setId(data[4].toString());
        if(data[5]!=null) personGroup.setGroupName(data[5].toString());
        System.out.println("data[4]:"+data[4]);
        person.setPersonGroup(personGroup);
        this.persons.add(person);
    }

    @Override
    public Object getValueAt(int row, int column) {//只有重写这个方法才可以保证编辑后数据正确
        Object superObject =super.getValueAt(row,column);
        Object value;
        Person person=this.persons.get(row);
//        PersonGroup personGroup=this.personGroups.get(row);
        if(person==null){
            person=new Person();

            this.persons.add(person);
        }
        if (column==0) {
            value = person.getId()==null?superObject:person.getId();
            if(person.getId()==null&&value!=null){
                person.setId(value.toString());
            }
        }else if (column==1){
            value = person.getName()==null?superObject:person.getName();
            if(person.getName()==null&&value!=null){
                person.setName(value.toString());
            }
        }else if (column==2) {
            value = person.getCreateDate()==null?superObject:DateTimeUtil.DateRepresentation.toString(person.getCreateDate(),DateTimeUtil.DateFormatString.yyyy_MM_ddHH$mm$ss);
            if(person.getCreateDate()==null&&value!=null){
                person.setCreateDate(DateTimeUtil.DateConvert.convertStringToDateTime(DateTimeUtil.DateFormatString.yyyy_MM_ddHH$mm$ss,value.toString()));
            }
        }else if (column==3){
            value = person.getDescription()==null?superObject:person.getDescription();
            if(person.getDescription()==null&&value!=null){
                person.setDescription(value.toString());
            }
        }else if (column==4) {
            PersonGroup personGroup=person.getPersonGroup()==null?new PersonGroup():person.getPersonGroup();

            value = personGroup.getId()==null?superObject:personGroup.getId();
            if(personGroup.getId()==null&&value!=null){
                personGroup.setId(value.toString());
            }
            person.setPersonGroup(personGroup);
        }else if (column==5) {
            PersonGroup personGroup=person.getPersonGroup()==null?new PersonGroup():person.getPersonGroup();

            value = personGroup.getGroupName()==null?superObject:personGroup.getGroupName();
            if(personGroup.getGroupName()==null&&value!=null){
                personGroup.setGroupName(value.toString());
            }
            person.setPersonGroup(personGroup);
        }else {
            value= superObject;
        }
        return value;
    }

    @Override
    public void setValueAt(Object aValue, int row, int column) {
        super.setValueAt(aValue,row,column);
        Person person=this.persons.get(row);
    }

    @Override
    public void removeRow(int row) {
        super.removeRow(row);
        this.persons.remove(row);
    }

    public List<Person> getPersons() {
        return persons;
    }
}
