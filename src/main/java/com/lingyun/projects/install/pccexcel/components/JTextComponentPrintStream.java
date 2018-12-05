package com.lingyun.projects.install.pccexcel.components;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.io.OutputStream;
import java.io.PrintStream;

public class JTextComponentPrintStream extends PrintStream {
    private JTextComponent textComponent;
    public static StringBuffer sb = new StringBuffer();
    public JTextComponentPrintStream(OutputStream out, JTextComponent textComponent) {
        super(out);
        this.textComponent=textComponent;
    }
    public void write(byte[] buf, int off, int len) {  
         final String message = new String(buf, off, len);
         SwingUtilities.invokeLater(new Runnable(){
         public void run(){
          sb.append(message+"\n");
             textComponent.setText(JTextComponentPrintStream.sb.toString());
         }
      });
   }

}
