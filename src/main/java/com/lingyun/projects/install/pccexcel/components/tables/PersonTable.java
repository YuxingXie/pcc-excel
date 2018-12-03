package com.lingyun.projects.install.pccexcel.components.tables;

import com.lingyun.common.support.util.string.StringUtils;
import com.lingyun.projects.install.pccexcel.components.basic.BasicTable;
import com.lingyun.projects.install.pccexcel.domain.persongroup.entity.PersonGroup;
import com.lingyun.projects.install.pccexcel.support.ComponentsDrawTools;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.util.List;
import java.util.Vector;

public class PersonTable extends BasicTable {
    private List<PersonGroup> personGroupList;
    private Vector<String> vector;
    public static final Object[] HEADER={"id(只读)","姓名","创建日期(只读)","备注","分组id(只读)","分组(点击可编辑)"};
    public PersonTable(List<PersonGroup> personGroupList) {
        super();
        this.personGroupList = personGroupList;

        this.vector=new Vector<>();

        if(personGroupList!=null){
            for(PersonGroup personGroup:this.personGroupList){
                if(StringUtils.isNotBlank(personGroup.getGroupName())){
                    vector.add(personGroup.getGroupName());
                }
            }
        }
        TableColumnModel tcm = getColumnModel();
        System.out.println("tcm.getColumnCount():"+tcm.getColumnCount());
//        TableColumn tc = tcm.getColumn(4) ;
//        tcm.removeColumn(tc);
    }
    @Override
    public TableCellEditor getCellEditor(int row, int column) {
        if (column==5){
            return new DefaultCellEditor(new JComboBox<>(vector));}
        if(column==0||column==2||column==4){return null;}//这几列不可编辑
        return super.getCellEditor(row,column);
    }

    @Override
    public TableCellRenderer getCellRenderer(int row, int column) {

//            return new TableCellRenderer() {
//                @Override
//                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
//
//                    Object obj = table.getValueAt(row, column);
////                    PersonGroup personGroup=obj==null?null:(PersonGroup)obj;
//                    String groupName = obj == null ? null : obj.toString();
//                    String personGroupId = ComponentsDrawTools.getGroupIdFromGroupName(groupName, PersonTable.this.personGroupList);
//                    table.setValueAt(personGroupId, row, 4);
//                    JLabel jLabel=new JLabel();
//                    jLabel.setText(groupName==null?null:groupName);
//                    return new JLabel("fu");
//                }};
        if(column==5){
            Object obj=this.getModel().getValueAt(row,column);
            String groupName = obj == null ? null : obj.toString();
            String personGroupId = ComponentsDrawTools.getGroupIdFromGroupName(groupName, PersonTable.this.personGroupList);
            this.setValueAt(personGroupId, row, 4);
        }
        return super.getCellRenderer(row, column);


    }

}
