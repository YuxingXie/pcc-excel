package com.lingyun.projects.install.pccexcel.support;

import com.lingyun.common.support.util.date.DateTimeUtil;
import com.lingyun.projects.install.pccexcel.domain.excel.entity.Excel;
import com.lingyun.projects.install.pccexcel.domain.persongroup.entity.PersonGroup;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.SortedMap;

public class ComponentsDrawTools {
    public static JTabbedPane drawTabbedPaneByExcel(Excel excel, JTabbedPane jTabbedpane) {
        jTabbedpane.removeAll();
        SortedMap<String, List<List<Object>>> result = excel.toSortedMap();
        for(String sheetName:result.keySet()){
            JPanel tabPanel=new JPanel(new BorderLayout());

            List<List<Object>> sheetData = result.get(sheetName);
            final Object[][] rowData=new Object[sheetData.size()][sheetData.get(0).size()];
            final Object[] columnNames=sheetData.get(0).toArray();
            for(int i=1;i<sheetData.size();i++){
                List<Object> rowList= sheetData.get(i);
                for (int j=0;j<rowList.size();j++){
                    rowData[i-1][j] =rowList.get(j);
                }
            }

            JTable table = new JTable(rowData,columnNames);

            JScrollPane jScrollPane=new JScrollPane(table);
            tabPanel.add(jScrollPane,BorderLayout.CENTER);


            jTabbedpane.addTab(sheetName,tabPanel);
        }


        return jTabbedpane;
    }

    public static Object[] getColumnNamesOfPersonGroups() {

        return new Object[]{"分组id(只读)","分组名称","描述","创建日期(只读)"};


    }
    public static Object[][] getRowDataOfPersonGroups(List<PersonGroup> personGroups) {

        Object[] columnNames=getColumnNamesOfPersonGroups();
        Object[][] rowData=new Object[personGroups.size()][columnNames.length];
        for(int i=0;i<personGroups.size();i++){
            PersonGroup personGroup=personGroups.get(i);
            rowData[i][0]=personGroup.getId();
            rowData[i][1]=personGroup.getGroupName();
            rowData[i][2]=personGroup.getDescription();
            rowData[i][3]= DateTimeUtil.DateRepresentation.toString(personGroup.getCreateDate(),DateTimeUtil.DateFormatString.yyyy_MM_ddHH$mm$ss);
        }

        return rowData;

    }
}
