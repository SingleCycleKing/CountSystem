package com.unique.countsystem.database;

import android.content.Context;
import android.os.Environment;

import com.unique.countsystem.R;
import com.unique.countsystem.Student;
import com.unique.countsystem.utils.DebugLog;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

/**
 * Excel handler
 * ******************
 * USER: ldx
 * EMAIL: danxionglei@foxmail.com
 * PROJECT_NAME: CountSystem
 * DATE: 2015/3/9
 */
public class ExcelHandler {
    public static final String WRITE_DIR_PATH = "countSystem";
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
        workbook.close();
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

    public void WriteExcel(Context context) throws IOException, BiffException {
        File cache = new File(Environment.getExternalStorageDirectory()
                .getAbsolutePath(), WRITE_DIR_PATH);
        if (!cache.exists()) {
            if (!cache.mkdirs()){
                throw new IOException("文件夹创建失败");
            }
        }
        String fileName = generateFileName();
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                +File.separator+WRITE_DIR_PATH+File.separator
                +fileName);
        WritableWorkbook workbook = Workbook.createWorkbook(file);
        WritableSheet sheet = workbook.createSheet("sheet0",0);
        int countTimes = DbHelper.getInstance().getSumCountTimes();

    }

    private void createSheetHead(WritableSheet sheet, Context context) throws WriteException {
        if (sheet == null) return;
        Label label1 = new Label(0,0,  context.getString(R.string.sheet_name));
        Label label2 = new Label(1,0,  context.getString(R.string.sheet_studentId));
        Label label3 = new Label(2,0,  context.getString(R.string.sheet_class));
        sheet.addCell(label1);
        sheet.addCell(label2);
        sheet.addCell(label3);
    }

    private final static String SAVE_NAME_FORMATER= "record_%s";

    private String generateFileName(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_mm_ss");
        Date date = new Date();
        String formated_date = dateFormat.format(date);
        return wrapXLS(String.format(SAVE_NAME_FORMATER, formated_date));
    }

    private String wrapXLS(String FileName){
        return FileName + ".xls";
    }

}
