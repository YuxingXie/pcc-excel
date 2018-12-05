package com.lingyun.projects.install.pccexcel.components;

import com.lingyun.projects.install.pccexcel.components.panels.TopComponent;

import javax.swing.*;

public class TextComponent extends TopComponent {
    private JTextField textField;

    public TextComponent(JTextField textField) {
        this.textField = textField;
        this.textField.setText(JTextComponentPrintStream.sb.toString());
        add(this.textField);
    }

    @Override
    public void loadData() {

        this.textField.setText(JTextComponentPrintStream.sb.toString());
    }

}
