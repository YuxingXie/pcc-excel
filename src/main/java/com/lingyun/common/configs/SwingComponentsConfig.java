package com.lingyun.common.configs;

import com.lingyun.common.support.util.clazz.BeanUtil;
import com.lingyun.common.support.util.file.OLE2OfficeExcelUtils;
import com.lingyun.projects.install.pccexcel.domain.excel.entity.Excel;
import com.lingyun.projects.install.pccexcel.domain.excel.service.ExcelService;
import com.lingyun.projects.install.pccexcel.support.ComponentsDrawTools;
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
    public JButton importExcelBtn(){
        JButton button=new JButton("导入excel...");
        return button;
    }
    @Bean
    public JTabbedPane excelDataPanel(ExcelService excelService,JButton importExcelBtn) throws IOException {
        Excel excel = excelService.findByLastOpenDateGreatest();
        JTabbedPane jTabbedpane = new JTabbedPane();
        if(excel==null) {
            JPanel tabPanel=new JPanel(new FlowLayout());
            JLabel label = new JLabel("当前没有excel文件");
            label.setForeground(Color.RED);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setVerticalTextPosition(SwingConstants.CENTER);

            tabPanel.add(label);
            tabPanel.add(importExcelBtn);
            jTabbedpane.addTab("",new ImageIcon(ClassLoader.getSystemResource("images/icon/icon.png")),tabPanel);
            return jTabbedpane;
        }
        return ComponentsDrawTools.drawTabbedPaneByExcel(excel, jTabbedpane);
    }


}
