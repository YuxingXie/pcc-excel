package com.lingyun.common.configs;

import com.lingyun.common.support.util.file.ExcelUtils;
import com.lingyun.projects.install.pccexcel.domain.service.ExcelService;
import javafx.stage.FileChooser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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

        JLabel label=new JLabel("双击单元格修改数据且不会覆盖原始文件，点击\"保存\"后会导出到excel中；点击\"刷新\"可以重新载入excel内容。");
        label.setHorizontalAlignment(SwingConstants.CENTER);


        File file = new File("D:\\document\\项目\\政协excel\\aa46e5671990fba676265d692abb543c.xls");
        SortedMap<String, List<List<Object>>> result = ExcelUtils.getData(file);
        JTabbedPane jTabbedpane = new JTabbedPane();
        for(String sheetName:result.keySet()){
            JPanel mainPanel=new JPanel(new BorderLayout());
            mainPanel.add(label,BorderLayout.NORTH);
            List<List<Object>> sheetData = result.get(sheetName);
            final Object[][] rowData=new Object[sheetData.size()][sheetData.get(0).size()];
            final Object[] columnNames=sheetData.get(1).toArray();
            for(int i=0;i<sheetData.size();i++){
                List<Object> rowList= sheetData.get(i);
                for (int j=0;j<rowList.size();j++){
                    rowData[i][j] =rowList.get(j);
                }
            }

            JTable table = new JTable(rowData,columnNames);

            JScrollPane jScrollPane=new JScrollPane(table);
            mainPanel.add(jScrollPane,BorderLayout.CENTER);
            JPanel buttonGroupPanel=new JPanel(new FlowLayout(FlowLayout.CENTER));
            JButton saveButton=new JButton("保存");
            saveButton.addActionListener(e -> {
                int rowCount=table.getRowCount();
                int columnCount=table.getColumnCount();
                for(int i=0;i<rowCount;i++){
                    for(int j=0;j<columnCount;j++){
                        String cellData=table.getValueAt(i,j).toString();
                        System.out.println(cellData);
                    }
                }

            });
            buttonGroupPanel.add(saveButton);
            buttonGroupPanel.add(new JButton("导出"));
            buttonGroupPanel.add(new JButton("刷新"));
            mainPanel.add(buttonGroupPanel,BorderLayout.SOUTH);
            jTabbedpane.addTab(sheetName,mainPanel);
        }



        return jTabbedpane;
    }
}
