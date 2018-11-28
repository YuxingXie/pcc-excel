package com.lingyun.common.configs;

import com.lingyun.common.support.util.file.OLE2OfficeExcelUtils;
import com.lingyun.projects.install.pccexcel.domain.excel.service.ExcelService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.SortedMap;

@Configuration
public class SwingComponentsConfig {
    @Bean
    public JPanel jPanelCenter(){
        JPanel jPanelCenter=new JPanel();
        jPanelCenter.setLayout(new BorderLayout());
        return jPanelCenter;
    }
@Bean
    public JFileChooser excelFileChooser(){
        JFileChooser fileChooser=new JFileChooser("/Users/xieyuxing/公司文档");
        FileFilter filter1 =new FileNameExtensionFilter("microsoft excel files","xls","xlsx","xlt","xml","xlsm","xlsb","xltx","xla","xlw","xlr");
        fileChooser.setFileFilter(filter1);
        return fileChooser;
    }
    @Bean
    public JTextField titleTextField(){
        JTextField textField=new JTextField();
        textField.setText("what's new?");
        textField.setEditable(false);
        return textField;
    }
    @Bean
    public JTabbedPane excelDataPanel(ExcelService excelService) throws IOException {




//        File file = new File("D:\\document\\项目\\政协excel\\aa46e5671990fba676265d692abb543c.xls");
        File file = new File("/Users/xieyuxing/公司文档/未命名的副本.xls");
        SortedMap<String, List<List<Object>>> result = OLE2OfficeExcelUtils.getData(file);
        JTabbedPane jTabbedpane = new JTabbedPane();
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
