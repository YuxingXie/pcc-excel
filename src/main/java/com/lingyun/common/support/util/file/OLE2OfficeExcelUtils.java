package com.lingyun.common.support.util.file;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;

import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.apache.poi.ss.usermodel.CellType.*;

public class OLE2OfficeExcelUtils {


    public static void printSortedMap(SortedMap<String, List<List<Object>>> result){
        System.out.println("start print sortedMap...");
        if(result==null) {
            System.out.println("sortedMap is null");
            return;
        }
        for( String sheetName:result.keySet()){
            List<List<Object>> sheetData=result.get(sheetName);
            System.out.println("sheet name:"+sheetName);
            for(List<Object> rowData:sheetData) {
                for(Object cellValue:rowData) {
                    System.out.print(cellValue+"\t\t\t");
                }
                System.out.println();
            }
        }
    }

    /**
     * 读取Excel的内容，SortedMap中key代表sheet name,value代表sheet内容
     * @param file 读取数据的源Excel
     * @return 读出的Excel中数据的内容
     * @throws FileNotFoundException
     * @throws IOException
     */

    public static SortedMap<String, List<List<Object>>> getData(File file) throws FileNotFoundException, IOException {

        SortedMap<String, List<List<Object>>> allData=new TreeMap<>();
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
        POIFSFileSystem fs = new POIFSFileSystem(in);
        HSSFWorkbook wb = new HSSFWorkbook(fs);
        for (int sheetIndex = 0; sheetIndex < wb.getNumberOfSheets(); sheetIndex++) {
            HSSFSheet sheet = wb.getSheetAt(sheetIndex);
            int lastRowNumber=sheet.getLastRowNum();
            List<List<Object>> sheetData=new ArrayList<>();
            for (int rowIndex = 0; rowIndex <= lastRowNumber; rowIndex++) {
                List<Object> rowData=handleRowOfSheet(sheet, rowIndex);
                sheetData.add(rowData);
            }
            allData.put(sheet.getSheetName(),sheetData);
        }

        in.close();
        return allData;
    }

    private static List<Object> handleRowOfSheet( HSSFSheet sheet, int rowIndex) {

        HSSFRow hssfRow = sheet.getRow(rowIndex);
        if (hssfRow == null) {
            return null;
        }
        List<Object> rowData = getRowData(hssfRow);
        return rowData;

    }

    private static List<Object> getRowData(HSSFRow hssfRow) {
        List<Object> rowData = new ArrayList<>();
        short cellCount = hssfRow.getLastCellNum();
        for (short cellIndex = 0; cellIndex <cellCount ; cellIndex++) {
                HSSFCell cell= hssfRow.getCell(cellIndex);
                Object cellValue="";
                if (cell != null) {
                    cellValue = getCellValue(cell);
                }
//                cellsDataOfRow.add(rightTrim(cellValue.toString()));
            rowData.add(cellValue);

        }
        return rowData;
    }


    private static Object getCellValue(HSSFCell cell) {
        Object cellValue="";
        CellType cellType=cell.getCellTypeEnum();
        switch (cellType) {
            case STRING:
                cellValue = cell.getStringCellValue();
                break;
            case NUMERIC:
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    Date date = cell.getDateCellValue();
                    if (date != null) {
                        cellValue = new SimpleDateFormat("yyyy-MM-dd").format(date);
                    } else {
                        cellValue = "";
                    }
                } else {
                    cellValue = new DecimalFormat("0").format(cell.getNumericCellValue());
                }
                break;
            case FORMULA:
                // 导入时如果为公式生成的数据则无值
//                                if (!cell.getStringCellValue().equals("")) {
                cell.setCellType(STRING);
                if (!cell.getStringCellValue().equals("")) {
                    cellValue = cell.getStringCellValue();
                } else {
                    cell.setCellType(NUMERIC);
                    cellValue = cell.getNumericCellValue() + "";
                }
                break;
            case BLANK:
                break;
            case ERROR:
                cellValue = "";
                break;
            case BOOLEAN:
                cellValue = (cell.getBooleanCellValue()  ? "Y"
                        : "N");
                break;
            default:
                cellValue = "";
        }
        return cellValue;
    }
}
