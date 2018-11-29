package com.lingyun.projects.install.pccexcel.components;

import com.lingyun.common.support.util.date.DateTimeUtil;
import com.lingyun.projects.install.pccexcel.domain.person.entity.Person;
import com.lingyun.projects.install.pccexcel.domain.persongroup.entity.PersonGroup;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

public class PersonTableModel extends DefaultTableModel {
    private List<Person> persons;
    public PersonTableModel(Object[][] data, Object[] columnNames) {
        super(data, columnNames);
        persons=new ArrayList<>();
        for(int i=0;i<data.length;i++){
            Person person=new Person();
            PersonGroup personGroup=new PersonGroup();
            person.setPersonGroup(personGroup);
            if(data[i][0]!=null) person.setId(data[i][0].toString());
            if(data[i][1]!=null) {
                personGroup.setGroupName(data[i][1].toString());
            }
            if(data[i][2]!=null) person.setName(data[i][2].toString());
            if(data[i][3]!=null) person.setCreateDate(DateTimeUtil.DateConvert.convertStringToDateTime(DateTimeUtil.DateFormatString.yyyy_MM_ddHH$mm$ss,data[i][3].toString()));
            if(data[i][4]!=null) personGroup.setId(data[i][4].toString());//隐藏列PersonGroup.id
            persons.add(person);
        }
    }
}
