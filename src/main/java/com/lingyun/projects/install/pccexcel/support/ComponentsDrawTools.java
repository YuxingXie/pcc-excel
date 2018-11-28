package com.lingyun.projects.install.pccexcel.support;

import com.lingyun.projects.install.pccexcel.domain.excel.entity.Excel;

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
}
