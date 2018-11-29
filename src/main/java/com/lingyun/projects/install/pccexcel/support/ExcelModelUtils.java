package com.lingyun.projects.install.pccexcel.support;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;

public class ExcelModelUtils {
    public static Object[][] getRowDataFromSortedMap(SortedMap<String, List<List<Object>>> sortedMap,int sheetIndex){
        assert(sheetIndex>=0 && sheetIndex<sortedMap.size());
        List<Object[][]> rowDatas=new ArrayList<>();
        for(String sheetName:sortedMap.keySet()){
            List<List<Object>> sheetData = sortedMap.get(sheetName);
            Object[][] rowData=new Object[sheetData.size()][sheetData.get(0).size()];
            Object[] columnNames=sheetData.get(0).toArray();
            for(int i=1;i<sheetData.size();i++){
                List<Object> rowList= sheetData.get(i);
                for (int j=0;j<rowList.size();j++){
                    rowData[i-1][j] =rowList.get(j);
                }
            }
            rowDatas.add(rowData);
        }
        return rowDatas.get(sheetIndex);
    }

    public static Object[] getColumnNamesFromSortedMap(SortedMap<String, List<List<Object>>> sortedMap,int sheetIndex) {
        assert (sheetIndex >= 0 && sheetIndex < sortedMap.size());
        List<Object[]> columnNamesList = new ArrayList<>();
        for (String sheetName : sortedMap.keySet()){
            List<List<Object>> sheetData = sortedMap.get(sheetName);
            Object[] columnNames = sheetData.get(0).toArray();
            columnNamesList.add(columnNames);
        }
        return columnNamesList.get(sheetIndex);
    }
}
