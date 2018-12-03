package com.lingyun.projects.install.pccexcel.components.panels;

import com.lingyun.common.support.util.clazz.BeanUtil;
import com.lingyun.common.support.util.date.DateTimeUtil;
import com.lingyun.common.support.util.string.StringUtils;
import com.lingyun.projects.install.pccexcel.components.tables.models.PersonGroupTableModel;
import com.lingyun.projects.install.pccexcel.components.basic.BasicTable;
import com.lingyun.projects.install.pccexcel.domain.persongroup.entity.PersonGroup;
import com.lingyun.projects.install.pccexcel.domain.persongroup.repo.PersonGroupRepository;
import com.lingyun.projects.install.pccexcel.support.ComponentsDrawTools;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class GroupManagerPanel extends TopComponent {
    private PersonGroupTableModel personGroupTableModel;
    private BasicTable personGroupTable;
    private JScrollPane personGroupScrollPane;
    private List<PersonGroup> personGroups;
    private PersonGroupRepository personGroupRepository;
    public GroupManagerPanel(PersonGroupRepository personGroupRepository) {
        this.setBackground(Color.LIGHT_GRAY);
        this.personGroupRepository=personGroupRepository;
        loadData();



    }

    @Override
    public void loadData() {

        this.personGroups =personGroupRepository.findAll();
        this.personGroupTableModel=new PersonGroupTableModel(ComponentsDrawTools.getRowDataOfPersonGroups(this.personGroups),ComponentsDrawTools.getColumnNamesOfPersonGroups());
        this.personGroupTable=new BasicTable();
        this.personGroupTable.setModel(this.personGroupTableModel);
        this.personGroupScrollPane=new JScrollPane(this.personGroupTable);
        renderGroupManagerPanel();
    }

    //TODO:Many problems
    public void renderGroupManagerPanel() {
//        this.setVisible(false);
        this.setVisible(true);
        setLayout(new BorderLayout());
        JPanel southPanel=new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton addBtn=new JButton("添加分组(添加后请点击保存)");
        southPanel.add(addBtn);
        JButton deleteBtn=new JButton("删除选中");
        southPanel.add(deleteBtn);
        JButton saveBtn=new JButton("保存编辑");
        southPanel.add(saveBtn);

        add(southPanel,BorderLayout.SOUTH);
        JPanel centerPanel=new JPanel(new BorderLayout());


        if(!BeanUtil.emptyCollection(this.personGroups)) {
            personGroupButtonEventBinding(addBtn, deleteBtn, saveBtn);
        }

        centerPanel.add(this.personGroupScrollPane,BorderLayout.CENTER);
        centerPanel.add(new JLabel("分组列表："),BorderLayout.NORTH);
        this.add(centerPanel,BorderLayout.CENTER);

    }
    private void personGroupButtonEventBinding(JButton addBtn, JButton deleteBtn, JButton saveBtn) {
        saveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<PersonGroup> personGroupsToAdd=new ArrayList<>();
                for(int i=0;i<GroupManagerPanel.this.personGroupTable.getRowCount();i++){
                    PersonGroup personGroup=new PersonGroup();
                    Object _id=GroupManagerPanel.this.personGroupTable.getValueAt(i,0);
                    if(_id!=null&&!_id.toString().trim().equals("")){
                        personGroup.setId(GroupManagerPanel.this.personGroupTable.getValueAt(i,0).toString());
                    }
                    Object groupName=GroupManagerPanel.this.personGroupTable.getValueAt(i,1);
                    if(groupName==null||groupName.toString().trim().equals("")){
                        JOptionPane.showMessageDialog(GroupManagerPanel.this,"第 "+(i+1)+" 行 分组名称不能为空");
                        return;
                    }
                    personGroup.setGroupName(groupName.toString());

                    Object description=GroupManagerPanel.this.personGroupTable.getValueAt(i,2);
                    if(description!=null&&!description.toString().trim().equals("")){
                        personGroup.setDescription(GroupManagerPanel.this.personGroupTable.getValueAt(i,2).toString());
                    }
                    Object createDate=GroupManagerPanel.this.personGroupTable.getValueAt(i,3);
                    if(createDate!=null&&!createDate.toString().trim().equals("")){
                        personGroup.setCreateDate(DateTimeUtil.DateConvert.convertStringToDateTime(DateTimeUtil.DateFormatString.yyyy_MM_ddHH$mm$ss,GroupManagerPanel.this.personGroupTable.getValueAt(i,3).toString()));
                    }
                    personGroupsToAdd.add(personGroup);
                }

                GroupManagerPanel.this.personGroups=GroupManagerPanel.this.personGroupRepository.save(personGroupsToAdd);
                System.out.println(BeanUtil.javaToJson(GroupManagerPanel.this.personGroups));
                GroupManagerPanel.this.personGroupTableModel.setPersonGroups(GroupManagerPanel.this.personGroups);
//                ExcelPanel.this.personGroupScrollPane.remove(ExcelPanel.this.personGroupTable);
                //TODO

//                ExcelPanel.this.personGroupScrollPane=new JScrollPane(ExcelPanel.this.personGroupTable);
                JOptionPane.showMessageDialog(GroupManagerPanel.this,"保存成功！");
//                ExcelPanel.this.personGroupTable.validate();
//                ExcelPanel.this.personGroupTable.updateUI();
            }
        });
        deleteBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row=GroupManagerPanel.this.personGroupTable.getSelectedRow();
//                int row=ExcelPanel.this.personGroupTable.rowAtPoint(e.getPoint());
                System.out.println("selected row:"+row);
                if(row<0){
                    JOptionPane.showMessageDialog(GroupManagerPanel.this,"请选择一条数据!");
                    return;
                }
                Object id=GroupManagerPanel.this.personGroupTable.getValueAt(row,0);
                if(id!=null&& StringUtils.isNotBlank(id.toString())){
                    GroupManagerPanel.this.personGroupRepository.delete(id.toString());
                    GroupManagerPanel.this.personGroups=personGroupRepository.findAll();
                }

                GroupManagerPanel.this.personGroupTableModel.removeRow(row);
                GroupManagerPanel.this.personGroupTableModel.setPersonGroups(GroupManagerPanel.this.personGroups);
                JOptionPane.showMessageDialog(GroupManagerPanel.this,"删除成功!");
//                ExcelPanel.this.personGroupTable.validate();
//                ExcelPanel.this.personGroupTable.updateUI();
            }
        });
        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Object[] newRow=new Object[4];
                GroupManagerPanel.this.personGroups.add(new PersonGroup());
                GroupManagerPanel.this.personGroupTableModel.addRow(newRow);
            }
        });
    }


}
