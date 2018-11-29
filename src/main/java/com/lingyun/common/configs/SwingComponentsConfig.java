package com.lingyun.common.configs;

import com.lingyun.common.support.util.clazz.BeanUtil;
import com.lingyun.common.support.util.file.OLE2OfficeExcelUtils;
import com.lingyun.projects.install.pccexcel.components.PersonFrame;
import com.lingyun.projects.install.pccexcel.config.Constant;
import com.lingyun.projects.install.pccexcel.domain.excel.entity.Excel;
import com.lingyun.projects.install.pccexcel.domain.excel.repo.ExcelRepository;
import com.lingyun.projects.install.pccexcel.domain.excel.service.ExcelService;
import com.lingyun.projects.install.pccexcel.domain.person.repo.PersonRepository;
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
    public JPanel excelParentPanel(){
        JPanel excelParentPanel=new JPanel();
        excelParentPanel.setLayout(new BorderLayout());
        return excelParentPanel;
    }
    @Bean
    public JPanel groupManagerPanel(){
        JPanel groupManagerPanel=new JPanel();
        groupManagerPanel.setBackground(Color.LIGHT_GRAY);
        return groupManagerPanel;
    }
    @Bean
    public PersonFrame personFrame(PersonRepository personRepository){
        return new PersonFrame(personRepository);
    }

    @Bean
    public JFileChooser excelFileChooser(ExcelRepository excelRepository){
        String currentDir;
        if(Constant.currentExcel!=null){
            currentDir=Constant.currentExcel.getPath();
        }else {
            Excel excel=excelRepository.findByLastOpenDateGreatest();
            currentDir=excel==null?null:excel.getPath();
        }
        JFileChooser fileChooser=new JFileChooser(currentDir);
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
            jTabbedpane.addTab("没有选择文件",tabPanel);
            return jTabbedpane;
        }
        return ComponentsDrawTools.drawTabbedPaneByExcel(excel, jTabbedpane);
    }


}
