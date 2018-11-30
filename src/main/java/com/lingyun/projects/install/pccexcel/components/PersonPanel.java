package com.lingyun.projects.install.pccexcel.components;

import com.lingyun.common.support.util.clazz.BeanUtil;
import com.lingyun.projects.install.pccexcel.domain.person.entity.Person;
import com.lingyun.projects.install.pccexcel.domain.person.repo.PersonRepository;
import com.lingyun.projects.install.pccexcel.domain.persongroup.entity.PersonGroup;
import com.lingyun.projects.install.pccexcel.domain.persongroup.repo.PersonGroupRepository;
import com.lingyun.projects.install.pccexcel.support.ComponentsDrawTools;

import javax.swing.*;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PersonPanel extends JPanel{
    private JScrollPane personScrollPane;
    private JTable personTable;
    private PersonTableModel model;
    private PersonRepository personRepository;
    private PersonGroupRepository personGroupRepository;
    private JComboBox<String> jComboBox;
    private JButton addBtn=new JButton("添加人员");
    private JButton deleteBtn=new JButton("删除选中");
    private JButton saveBtn=new JButton("保存");
    private java.util.List<Person> persons;
    public PersonPanel(PersonRepository personRepository,PersonGroupRepository personGroupRepository) {
        this.personRepository=personRepository;
        setLayout(new BorderLayout());

        this.persons=this.personRepository.findAll();

        java.util.List<PersonGroup> personGroupList=personGroupRepository.findAll();

        this.personTable = new PersonTable(personGroupList);
        model=new PersonTableModel(ComponentsDrawTools.getRowDataOfPersonTable(persons), PersonTable.HEADER);

        this.personTable.setModel(model);
        TableColumnModel tcm = this.personTable.getColumnModel();
        System.out.println("tcm.getColumnCount():"+tcm.getColumnCount());
        TableColumn tc = tcm.getColumn(4) ;
//        tcm.removeColumn(tc);
        System.out.println("tcm.getColumnCount():"+tcm.getColumnCount());
        personScrollPane = new JScrollPane(this.personTable);
        add(personScrollPane,BorderLayout.CENTER);

        JPanel buttonGroupPanel=new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonGroupPanel.add(this.addBtn);
        buttonGroupPanel.add(this.deleteBtn);
        buttonGroupPanel.add(this.saveBtn);
        add(buttonGroupPanel,BorderLayout.SOUTH);
        JPanel northPanel=new JPanel(new FlowLayout(FlowLayout.LEFT));
        northPanel.add(new JLabel("人员列表   |    筛选条件"));
        String str1[] = {"全部"};
        this.jComboBox=new JComboBox<>( str1);
        this.jComboBox.addItem("同名");
        this.jComboBox.addItem("未分组");
        this.jComboBox.addItem("已分组");
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
            }
        });
    }
}
