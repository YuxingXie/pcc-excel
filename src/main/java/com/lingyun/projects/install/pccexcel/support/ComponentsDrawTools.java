package com.lingyun.projects.install.pccexcel.support;

import com.lingyun.common.support.util.clazz.BeanUtil;
import com.lingyun.common.support.util.date.DateTimeUtil;
import com.lingyun.common.support.util.file.OLE2OfficeExcelUtils;
import com.lingyun.common.support.util.file.XLSExcelUtils;
import com.lingyun.common.support.util.string.StringUtils;
import com.lingyun.projects.install.pccexcel.components.basic.BasicTable;
import com.lingyun.projects.install.pccexcel.components.panels.ExcelExportReviewPanel;
import com.lingyun.projects.install.pccexcel.components.tables.PersonTable;
import com.lingyun.projects.install.pccexcel.config.Constant;
import com.lingyun.projects.install.pccexcel.domain.excel.entity.Excel;
import com.lingyun.projects.install.pccexcel.domain.excel.entity.ExcelData;
import com.lingyun.projects.install.pccexcel.domain.excel.service.ExcelService;
import com.lingyun.projects.install.pccexcel.domain.person.entity.Person;
import com.lingyun.projects.install.pccexcel.domain.persongroup.entity.PersonGroup;
import org.springframework.core.io.ClassPathResource;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.List;

public class ComponentsDrawTools {
    public static void expandTree(JTree tree) {
        TreeNode root = (TreeNode) tree.getModel().getRoot();
        expandAll(tree, new TreePath(root), true);
    }
    private static void expandAll(JTree tree, TreePath parent, boolean expand) {
        // Traverse children
        TreeNode node = (TreeNode) parent.getLastPathComponent();
        if (node.getChildCount() >= 0) {
            for (Enumeration e = node.children(); e.hasMoreElements(); ) {
                TreeNode n = (TreeNode) e.nextElement();
                TreePath path = parent.pathByAddingChild(n);
                expandAll(tree, path, expand);
            }
        }

        // Expansion or collapse must be done bottom-up
        if (expand) {
            tree.expandPath(parent);
        } else {
            tree.collapsePath(parent);
        }
    }
    public static int openFileChooser(ExcelService excelService, JComponent parent) {
        String currentDir;
        if(Constant.currentExcel!=null){
            currentDir=Constant.currentExcel.getPath();
        }else {
            Constant.currentExcel=excelService.findByLastOpenDateGreatest();
            currentDir=Constant.currentExcel==null?null:Constant.currentExcel.getPath();
        }
        JFileChooser excelFileChooser=new JFileChooser(currentDir);
        FileFilter filter1 =new FileNameExtensionFilter("microsoft excel files","xls","xlsx","xlt","xml","xlsm","xlsb","xltx","xla","xlw","xlr");
        excelFileChooser.setFileFilter(filter1);
        parent.add(excelFileChooser, BorderLayout.CENTER);
        int result = excelFileChooser.showOpenDialog(parent);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = excelFileChooser.getSelectedFile();
            Excel excel = excelService.findByFilePath(file.getAbsolutePath());
            String json= null;
            try {
                json = BeanUtil.javaToJson(OLE2OfficeExcelUtils.getData(file));
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            if(excel==null){
                excel=new Excel();
                excel.setPath(file.getAbsolutePath());

            }
            excel.setLastOpenDate(new Date());
            excel.setDataJson(json==null?null:json.getBytes(StandardCharsets.UTF_8));
//            System.out.println(new String(excel.getDataJson(),StandardCharsets.UTF_8));
            excel=excelService.save(excel);
            Constant.currentExcel=excel;


        }


        return result;
    }
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

        Map<String, List<ExcelData>> groupedMap=new HashMap<>();
        List<String> groupNames=new ArrayList<>();
        for(ExcelData excelData:excelDataList){
            String groupName= getGroupName(excelData);
            if (!groupNames.contains(groupName)){
                groupNames.add(groupName);
            }
            List<ExcelData> excelDataListForGroupName = groupedMap.computeIfAbsent(groupName, k -> new ArrayList<>());
            excelDataListForGroupName.add(excelData);
        }
        //需要排序
        Map<String, List<ExcelData>> sortedMap=new HashMap<>();
        for(String key:groupedMap.keySet()){
            List<ExcelData> excelDatas=groupedMap.get(key);
            List<ExcelData> sortedExcelDatas=sortListByTotalCount(excelDatas);
            sortedMap.put(key,sortedExcelDatas);
        }
        return sortedMap;
    }

    private static List<ExcelData> sortListByTotalCount(List<ExcelData> excelDatas) {
        Collections.sort(excelDatas, new Comparator<ExcelData>() {
            @Override
            public int compare(ExcelData o1, ExcelData o2) {
                return o2.getTotalCount()-o1.getTotalCount();
            }
        });
        return excelDatas;

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

    public static void exportExcel(List<ExcelData> excelDataList,ExcelService excelService,JComponent fileChooserParentComponent) {
        String currentDir;
        if(Constant.currentExcel!=null){
            currentDir=Constant.currentExcel.getPath();
        }else {
            Excel excel=excelService.findByLastOpenDateGreatest();
            currentDir=excel==null?null:excel.getPath();
        }
        JFileChooser fileChooser=new JFileChooser(currentDir);
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooserParentComponent.add(fileChooser, BorderLayout.CENTER);
        int result = fileChooser.showOpenDialog(fileChooserParentComponent);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            String fileName= DateTimeUtil.DateRepresentation.toString(new Date(),DateTimeUtil.DateFormatString.yyyyMMddHHmmss)+".xls";
            String exportFile=file.getAbsolutePath()+File.separator+fileName;
            ClassPathResource resource=new ClassPathResource("templates/template.xls");
            Map<String, List<ExcelData>> data= ComponentsDrawTools.excelDataListToMap(excelDataList);
            for (Map.Entry<String, List<ExcelData>>entry:data.entrySet()){

            }
            InputStream in = null;
            final String[] columnNames=new String[]{"排名","姓名","登录次数","浏览次数","点赞次数","评论次数","分享次数","总次数"};
            try {
                in=resource.getInputStream();
                XLSExcelUtils.exportExcelFromInputStream(in,exportFile,data,columnNames,0,0,true);
                JOptionPane.showMessageDialog(fileChooserParentComponent,"文件“"+exportFile+"”导出成功!");
            } catch (IOException e1) {
                e1.printStackTrace();
                JOptionPane.showMessageDialog(fileChooserParentComponent,"程序异常!","警告:",JOptionPane.WARNING_MESSAGE);
            }finally {
                if (null != in) {
                    try {
                        in.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        }
    }

}
