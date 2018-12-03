package com.lingyun.projects.install.pccexcel.components.panels;

import com.lingyun.common.support.util.clazz.BeanUtil;
import com.lingyun.projects.install.pccexcel.components.tables.PersonTable;
import com.lingyun.projects.install.pccexcel.domain.person.entity.Person;
import com.lingyun.projects.install.pccexcel.domain.person.repo.PersonRepository;
import com.lingyun.projects.install.pccexcel.domain.persongroup.entity.PersonGroup;
import com.lingyun.projects.install.pccexcel.domain.persongroup.repo.PersonGroupRepository;
import com.lingyun.projects.install.pccexcel.support.ComponentsDrawTools;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PersonPanel extends TopComponent {
    private JScrollPane personScrollPane;
    private JTable personTable;
    private PersonTableModel model;
    private PersonRepository personRepository;
    private PersonGroupRepository personGroupRepository;
    private JComboBox<String> jComboBox;
    private JButton addBtn;
    private JButton deleteBtn;
    private JButton saveBtn;
    private java.util.List<Person> persons;
    private java.util.List<PersonGroup> personGroupList;
    public PersonPanel(PersonRepository personRepository,PersonGroupRepository personGroupRepository) {
        this.personRepository=personRepository;
        this.personGroupRepository=personGroupRepository;
        loadData();
    }


    @Override
    public void loadData()  {
        this.persons=this.personRepository.findAll();
        this.personGroupList=this.personGroupRepository.findAll();
        this.personTable = new PersonTable(personGroupList);
        model=new PersonTableModel(ComponentsDrawTools.getRowDataOfPersonTable(persons), PersonTable.HEADER);
        this.personTable.setModel(model);
        personScrollPane = new JScrollPane(this.personTable);
        add(personScrollPane,BorderLayout.CENTER);
        JPanel buttonGroupPanel=new JPanel(new FlowLayout(FlowLayout.CENTER));
        addBtn=new JButton("添加人员");
        deleteBtn=new JButton("删除选中");
        saveBtn=new JButton("保存");
        buttonGroupPanel.add(this.addBtn);
        buttonGroupPanel.add(this.deleteBtn);
        buttonGroupPanel.add(this.saveBtn);
        add(buttonGroupPanel,BorderLayout.SOUTH);
        JPanel northPanel=new JPanel(new FlowLayout(FlowLayout.LEFT));
        northPanel.add(new JLabel("人员列表   |    筛选条件"));
        String str1[] = {"全部","同名","未分组","已分组"};
        this.jComboBox=new JComboBox<>( str1);
        northPanel.add(this.jComboBox);
        add(northPanel,BorderLayout.NORTH);
        buttonsActionBinding();
    }

    private void buttonsActionBinding() {
        this.addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PersonPanel.this.model.addRow(new Object[PersonTable.HEADER.length]);
                /**
                 *      返回底部
                 */
                Rectangle rect = PersonPanel.this.personTable.getCellRect(PersonPanel.this.personTable.getRowCount() -1, 0, true);
                PersonPanel.this.personTable.scrollRectToVisible(rect);
            }
        });
        this.saveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(BeanUtil.javaToJson(PersonPanel.this.model.getPersons()));
                personRepository.save(PersonPanel.this.model.getPersons());
                JOptionPane.showMessageDialog(PersonPanel.this,"保存成功!","提示:",JOptionPane.INFORMATION_MESSAGE);
                PersonPanel.this.reload();
            }
        });
    }


}
