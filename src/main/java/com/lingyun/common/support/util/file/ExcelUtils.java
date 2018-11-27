package com.lingyun.common.support.util.file;

public class ExcelUtils {
    public static String[] getColumnNames(String filePath){
        assert(filePath!=null);
        String[] columnNames = new String[10];
        for(int i=0;i<10;i++){
            String columnName="column "+i;
            columnNames[i]= columnName;
        }
        return columnNames;
    }

    public static Object[][] getRowData(String filePath){
        assert(filePath!=null);
        Object[][] rowData = new Object[8][10];
        for(int i=0;i<8;i++){
            String[] columns=new String[10];
            for(int j=0;j<10;j++){
                columns[j] = "row "+i+" column "+j;
            }

            rowData[i]= columns;
        }
        return rowData;
    }

    public static void main(String[] args){
        Object[][] rowData = getRowData("");
        System.out.println(rowData);
    }
}
