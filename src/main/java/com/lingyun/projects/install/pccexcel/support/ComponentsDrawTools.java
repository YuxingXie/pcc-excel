package com.lingyun.projects.install.pccexcel.support;

import com.lingyun.common.support.util.clazz.BeanUtil;
import com.lingyun.common.support.util.date.DateTimeUtil;
import com.lingyun.common.support.util.string.StringUtils;
import com.lingyun.projects.install.pccexcel.components.BasicTable;
import com.lingyun.projects.install.pccexcel.components.PersonTable;
import com.lingyun.projects.install.pccexcel.domain.excel.entity.Excel;
import com.lingyun.projects.install.pccexcel.domain.excel.entity.ExcelData;
import com.lingyun.projects.install.pccexcel.domain.person.entity.Person;
import com.lingyun.projects.install.pccexcel.domain.persongroup.entity.PersonGroup;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.*;
import java.util.List;

public class ComponentsDrawTools {

    public static JTabbedPane drawTabbedPaneOfExcelExportReview(List<ExcelData> excelDataList){
        Map<String,List<ExcelData>> groupedExcelDataMap= excelDataListToMap(excelDataList);
        JTabbedPane jTabbedPane=new JTabbedPane();
        final Object[] columnNames=new Object[]{"排名","姓名","登录次数","浏览次数","点赞次数","评论次数","分享次数","总次数"};
        for (String sheetName:groupedExcelDataMap.keySet()){
            final Object[][] rowData = getRowDataOfSheet(groupedExcelDataMap, sheetName);
            JPanel tabPanel=new JPanel(new BorderLayout());
            JTable table = new BasicTable(rowData,columnNames){

                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            JScrollPane jScrollPane=new JScrollPane(table);
            tabPanel.add(jScrollPane,BorderLayout.CENTER);
            jTabbedPane.addTab(sheetName+"排名",tabPanel);
        }
        return jTabbedPane;
    }

    private static Object[][] getRowDataOfSheet(Map<String, List<ExcelData>> groupedExcelDataMap, String sheetName) {
        List<ExcelData> mappedExcelDataList=groupedExcelDataMap.get(sheetName);
        return generateRowDataForReview(mappedExcelDataList);
    }

    private static Object[][] generateRowDataForReview(List<ExcelData> mappedExcelDataList) {
        Object[][] rowData=new Object[mappedExcelDataList.size()][8];
        int i=0;
        for(ExcelData excelData:mappedExcelDataList){
            rowData[i][0]=i+1;//排名
            rowData[i][1]=excelData.getPerson().getName();
            rowData[i][2]=excelData.getLoginCount();
            rowData[i][3]=excelData.getViewCount();
            rowData[i][4]=excelData.getPraiseCount();
            rowData[i][5]=excelData.getCommentCount();
            rowData[i][6]=excelData.getShareCount();
            rowData[i][7]=excelData.getTotalCount();

            i++;
        }
        return rowData;
    }

    public static Map<String, List<ExcelData>> excelDataListToMap(List<ExcelData> excelDataList) {

        Map<String, List<ExcelData>> map=new HashMap<>();
        List<String> groupNames=new ArrayList<>();
        for(ExcelData excelData:excelDataList){
            String groupName= getGroupName(excelData);
            if (!groupNames.contains(groupName)){
                groupNames.add(groupName);
            }
            List<ExcelData> excelDataListForGroupName = map.computeIfAbsent(groupName, k -> {
                return new ArrayList<>();
            });
            excelDataListForGroupName.add(excelData);

        }
        return map;
    }

    private static String getGroupName(ExcelData excelData) {
        String groupName="未分组";
        if (excelData.getPerson()==null) return groupName;
        if (excelData.getPerson().getPersonGroup()==null) return groupName;
        if (StringUtils.isBlank(excelData.getPerson().getPersonGroup().getGroupName())) return groupName;
        return excelData.getPerson().getPersonGroup().getGroupName();
    }


    public static Object[][] getRowDataOfPersonTable(List<Person> persons) {

        Object[] columnNames = PersonTable.HEADER;
        Object[][] rowData = new Object[persons.size()][columnNames.length];
        for (int i = 0; i < persons.size(); i++) {
            Person person = persons.get(i);
            PersonGroup personGroup=person.getPersonGroup();
            rowData[i][0] = person.getId();
            rowData[i][1] = person.getName();
            rowData[i][2] = DateTimeUtil.DateRepresentation.toString(person.getCreateDate(), DateTimeUtil.DateFormatString.yyyy_MM_ddHH$mm$ss);
            rowData[i][3] = person.getDescription();
            rowData[i][4] = personGroup==null?null:personGroup.getId();
            rowData[i][5] = personGroup==null?null:personGroup.getGroupName();
        }
        return rowData;
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
    public static String getGroupIdFromGroupName(String groupName,List<PersonGroup> personGroupList) {
        if (StringUtils.isBlank(groupName)) return null;
        if (BeanUtil.emptyCollection(personGroupList)) return null;
        for(PersonGroup personGroup:personGroupList){
            if (personGroup.getGroupName().trim().equals(groupName.trim())){
                return personGroup.getId();
            }
        }
        return null;
    }
}
