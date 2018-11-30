package com.lingyun.projects.install.pccexcel.components;

import com.lingyun.projects.install.pccexcel.domain.person.entity.Person;
import com.lingyun.projects.install.pccexcel.domain.person.repo.PersonRepository;
import com.lingyun.projects.install.pccexcel.support.ComponentsDrawTools;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PersonPanel extends JPanel{
    private JScrollPane personScrollPane;
    private JTable personTable;
    private PersonTableModel model;
    private PersonRepository personRepository;
    private JComboBox<String> jComboBox;
    private JButton addBtn=new JButton("添加人员");
    private JButton deleteBtn=new JButton("删除选中");
    private JButton saveBtn=new JButton("保存");
    private java.util.List<Person> persons;
    public PersonPanel(PersonRepository personRepository) {
        this.personRepository=personRepository;
        setLayout(new BorderLayout());
        personTable = new JTable();
        java.util.List<Person> persons=this.personRepository.findAll();
        model=new PersonTableModel(ComponentsDrawTools.getRowDataOfPersons(persons), ComponentsDrawTools.getColumnNamesOfPersons());

        personTable.setModel(model);
        personScrollPane = new JScrollPane(personTable);
        add(personScrollPane,BorderLayout.CENTER);

        JPanel buttonGroupPanel=new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonGroupPanel.add(addBtn);
        buttonGroupPanel.add(deleteBtn);
        buttonGroupPanel.add(saveBtn);
        add(buttonGroupPanel,BorderLayout.SOUTH);
        JPanel northPanel=new JPanel(new FlowLayout(FlowLayout.LEFT));
        northPanel.add(new JLabel("人员列表   |    筛选条件"));
        String str1[] = {"全部"};
        this.jComboBox=new JComboBox<>( str1);
        this.jComboBox.addItem("未分组");
        this.jComboBox.addItem("同名");
        northPanel.add(this.jComboBox);
        add(northPanel,BorderLayout.NORTH);

        buttonsActionBinding();
    }

    private void buttonsActionBinding() {
        this.saveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }
}
