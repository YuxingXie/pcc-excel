package com.lingyun.common.configs;

import com.lingyun.common.support.swing.components.SimpleStringComponent;
import javafx.stage.FileChooser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

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
}
