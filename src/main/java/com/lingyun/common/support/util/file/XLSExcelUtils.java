package com.lingyun.common.support.util.file;

import com.lingyun.common.support.data.Excelable;
import com.lingyun.projects.install.pccexcel.domain.excel.entity.ExcelData;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;

import java.io.*;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class XLSExcelUtils {
    public static void main(String[] args) {
        int i = 0;
        Object obj = i;
        System.out.println(obj.getClass().isAssignableFrom(Integer.class));
    }

    /**
     * @param inputStream
     * @param toFilePath
     * @param data
     * @param headers
     * @param beginRow         在excel中第几行开始
     * @param beginColumn      在excel列中第几列开始
     * @param firstColumnOrder 第一列是否为序号
     * @param <T>
     */
    public static <T extends Excelable> void exportExcelFromInputStream(InputStream inputStream, String toFilePath, Map<String, List<T>> data, String[] headers, int beginRow, int beginColumn, boolean firstColumnOrder) {

        try {
            Workbook  workbook = WorkbookFactory.create(inputStream);
            for(int i=0;i<workbook.getNumberOfSheets();i++){
                workbook.removeSheetAt(i);
            }
            FileOutputStream fos = new FileOutputStream(toFilePath);
            int dataColumns = headers.length;
            for (String sheetName : data.keySet()) {
                Sheet sheet = workbook.createSheet(sheetName + "排名");
                List<T> sheetData = data.get(sheetName);
                int order = 0;
                int rowIndex=beginRow;
                writeHeaders(headers, sheet,rowIndex);
                for (Excelable rowData : sheetData) {
                    int columnIndex=beginColumn;
                    Row row = sheet.createRow(++rowIndex);
                    if (firstColumnOrder) {
                        Cell orderCell = row.createCell(columnIndex++);
                        orderCell.setCellValue(++order);
                    }
                    for (int column = columnIndex; column < beginColumn + dataColumns; column++) {
                        Cell cell = row.createCell(column);
//                    if (cell == null) cell = row.createCell(column);
                        Map<String, Object> rowDataMap = rowData.toExcelRow();
                        String columnName = headers[column];
                        Object cellValue = getCellValueFromMap(rowDataMap, columnName);
                        if (cellValue != null) {
                            if (cellValue instanceof Boolean) cell.setCellValue((Boolean) cellValue);
                            else if (cellValue.getClass().isAssignableFrom(String.class))
                                cell.setCellValue((String) cellValue);
                            else if (cellValue.getClass().isAssignableFrom(Date.class))
                                cell.setCellValue((Date) cellValue);
                            else if (cellValue.getClass().isAssignableFrom(Number.class))
                                cell.setCellValue((Double) cellValue);
                            else if (cellValue.getClass().isAssignableFrom(Calendar.class))
                                cell.setCellValue((Calendar) cellValue);
                            else if (cellValue.getClass().isAssignableFrom(RichTextString.class))
                                cell.setCellValue((RichTextString) cellValue);
                            else cell.setCellValue(cellValue.toString());
                        }
                    }

                }
            }

            workbook.write(fos);
            InputStream  fis = new BufferedInputStream(new FileInputStream(toFilePath));
            byte[]  buffer = new byte[fis.available()];
            fis.read(buffer);
            fos.flush();
            fos.close();
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private static Object getCellValueFromMap(Map<String, Object> rowDataMap, String columnName) {
        Object cellValue = null;
        for (Map.Entry<String, Object> entry : rowDataMap.entrySet()) {
            String _columnName = entry.getKey();
            Object _cellValue = entry.getValue();
            if (columnName.equals(_columnName)) cellValue = _cellValue;
        }
        return cellValue;
    }

    private static void writeHeaders(String[] headers, Sheet sheet,int startRow) {
        Row headerRow = sheet.createRow(startRow);
        for (int i = 0; i < headers.length; i++) {
            String header = headers[i];
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(header);
        }
    }

    public static void exportExcel(String templateFilePath, String tempFilePath, List<Map<String, Object>> data, int beginRow, int beginCell, int dataColumns) {
        //TODO 这里只能导出xls格式，如果xlsx格式则需要HSSFWorkbook
        File newFile = createNewFileFromTemplate(templateFilePath, tempFilePath);
        // File newFile = new File("d:/ss.xls");
        // 新文件写入数据，并下载*****************************************************
        InputStream is = null;
        Workbook workbook = null;
        Sheet sheet = null;
        try {
            is = new FileInputStream(newFile);// 将excel文件转为输入流
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
//            exportExcelFromInputStream(is,tempFilePath,data,beginRow,beginCell,dataColumns);
    }

    /**
     * 读取excel模板，并复制到新文件中供写入和下载
     *
     * @param templateFilePath
     * @return
     */
    public static File createNewFileFromTemplate(String templateFilePath, String tempFiePath) {
        // 读取模板，并赋值到新文件************************************************************
        // 文件模板路径
//        String path = (getSispPath() + "uploadfile/违法案件报表.xlsx");
        String path = templateFilePath;
        File file = new File(path);
        // 保存文件的路径
        String realPath = tempFiePath;
        // 新的文件名
        String newFileName = System.currentTimeMillis() + ".xlsx";
        // 判断路径是否存在
        File dir = new File(realPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        // 写入到新的excel
        File newFile = new File(realPath, newFileName);
        try {
            newFile.createNewFile();
            // 复制模板到新文件
            fileChannelCopy(file, newFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newFile;
    }

    /**
     * 复制文件
     *
     * @param s 源文件
     * @param t 复制到的新文件
     */

    public static void fileChannelCopy(File s, File t) {
        try {
            InputStream in = null;
            OutputStream out = null;
            try {
                in = new BufferedInputStream(new FileInputStream(s), 1024);
                out = new BufferedOutputStream(new FileOutputStream(t), 1024);
                byte[] buffer = new byte[1024];
                int len;
                while ((len = in.read(buffer)) != -1) {
                    out.write(buffer, 0, len);
                }
            } finally {
                if (null != in) {
                    in.close();
                }
                if (null != out) {
                    out.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
