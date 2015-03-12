package com.unique.countsystem.database;

import com.unique.countsystem.Student;
import com.unique.countsystem.utils.DebugLog;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

/**
 * Excel handler
 * ******************
 * USER: ldx
 * EMAIL: danxionglei@foxmail.com
 * PROJECT_NAME: CountSystem
 * DATE: 2015/3/9
 */
public class ExcelHandler {
    /**
     * Singleton instance
     */
    private static ExcelHandler instance;

    /**
     * Singleton method
     * @return ExcelHandler
     */
    public static ExcelHandler getInstance(){
        if (instance == null){
            instance = new ExcelHandler();
        }
        return instance;
    }

    /**
     * Read the File to database. Need time.
     * @param file excel file selected
     * @throws IOException 读写错误
     * @throws BiffException 文件损坏或格式错误
     */
    public void ReadExcel(File file) throws IOException, BiffException {
        if (file==null||!file.exists()||!file.isFile()){
            throw new IllegalArgumentException("ReadExcel error: 文件可能损坏");
        }
        Workbook workbook = Workbook.getWorkbook(file);
        Sheet sheet = workbook.getSheet(0);
        int rowsNumber = sheet.getRows();
//        int columnNumber = sheet.getColumns();
        DebugLog.d(""+ rowsNumber);
        List<Student> excelStudentList = new ArrayList<>(rowsNumber);
        String name;
        String studentId;
        String _class;
        for(int i =1; i <=rowsNumber; i++){
            if (isANullRow(sheet,i)){
                DebugLog.d("null");
                break;
            }
            name = sheet.getCell(0, i).getContents();
            studentId = sheet.getCell(1, i).getContents();
            _class = sheet.getCell(2, i).getContents();
            DebugLog.d(name + " ; " + studentId + " ; " + _class);
            excelStudentList.add(DbHelper.createStudentModel(name,studentId,_class));
        }
        if(excelStudentList.isEmpty()) return;
        DbHelper.getInstance().insertStudentsList(excelStudentList);
    }

    private boolean isANullRow(Sheet sheet, int rowsNumber){
        int columns = sheet.getColumns();
        String cell;
        for( int j =0; j<= columns ; j++){
            cell = sheet.getCell(j, rowsNumber).getContents();
            if(cell==null||cell.length()==0){
                continue;
            }
            return false;
        }
        return true;
    }

    private int getRightRows(Sheet sheet){
        if (sheet == null){
            return 0;
        }
        int rsCols = sheet.getColumns();
        int rsRows = sheet.getRows();
        int nullCellNum;
        for(int i=1;i<rsRows;i++){
            nullCellNum = 0;
            for (int j = 0; j < rsCols; j++) {
                String val = sheet.getCell(j, i).getContents();
            }
        }
        return 1;
    }


    public void WriteExcel(File file) throws IOException, BiffException {

    }




}
