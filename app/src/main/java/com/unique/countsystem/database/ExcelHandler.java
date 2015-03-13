package com.unique.countsystem.database;

import android.content.Context;
import android.os.Environment;

import com.unique.countsystem.R;
import com.unique.countsystem.Record;
import com.unique.countsystem.RecordTime;
import com.unique.countsystem.Student;
import com.unique.countsystem.database.model.absenceType;
import com.unique.countsystem.utils.DebugLog;

import java.io.File;
import java.io.IOException;
import java.io.WriteAbortedException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    public static final String WRITE_DIR_PATH = "/countSystem/";
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
//        DebugLog.d(""+ rowsNumber);
        List<Student> excelStudentList = new ArrayList<>(rowsNumber);
        Student student;
        String name;
        String studentId;
        String _class;
        for(int i =1; i <rowsNumber; i++){
            if (isANullRow(sheet,i)){
                DebugLog.d("null");
                break;
            }
            name = sheet.getCell(0, i).getContents();
            studentId = sheet.getCell(1, i).getContents();
            _class = sheet.getCell(2, i).getContents();
            DebugLog.d(name + " ; " + studentId + " ; " + _class);
            student = DbHelper.createStudentModel(name,studentId,_class);
            if (DbHelper.getInstance().getStudentByStudentId(student.getStudentId())!=null){
                continue;
            }
            excelStudentList.add(student);
        }
        workbook.close();
        if(excelStudentList.isEmpty()) return;
        DbHelper.getInstance().insertStudentsList(excelStudentList);
    }

    private boolean isANullRow(Sheet sheet, int rowsNumber){
        int columns = sheet.getColumns();
        DebugLog.d(""+columns);
        String cell;
        for( int j =0; j< columns ; j++){
            cell = sheet.getCell(j, rowsNumber).getContents();
            if(cell==null||cell.length()==0){
                continue;
            }
            return false;
        }
        return true;
    }

    public void WriteExcel(Context context) throws IOException, BiffException, WriteException {
        File cache = new File(Environment.getExternalStorageDirectory()
                .getAbsolutePath(), WRITE_DIR_PATH);
        if (!cache.exists()) {
            if (!cache.mkdirs()){
                throw new IOException("文件夹创建失败");
            }
            DebugLog.e("create dirs success");
        }
        String fileName = generateFileName();
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                +WRITE_DIR_PATH
                +fileName);
        DebugLog.e(file.getPath());
        WritableWorkbook workbook = Workbook.createWorkbook(file);
        WritableSheet sheet = workbook.createSheet("sheet0", 0);
        createSheetHead(sheet,context);
        createSheetBody(sheet,context);
        workbook.write();
        workbook.close();
    }

    private void createSheetBody(WritableSheet sheet, Context context) throws WriteException {
        if (sheet ==null) return;
        List<Student> students = DbHelper.getInstance().getAllStudents();
        List<RecordTime> recordTimes = DbHelper.getInstance().getAllRecordTimeAscOrdered();
        List<Record> recordList;
        Label label;
        Label name;
        Label studentId;
        Label _class;
        Label sum;
        int sum_counter;
        for (int i = 1;i<students.size()+1;i++){
            sum_counter = 0;
            name = new Label(0,i,students.get(i-1).getName());
            studentId = new Label(1,i,students.get(i-1).getStudentId());
            _class = new Label(2,i,students.get(i-1).getStudentId());
            sheet.addCell(name);
            sheet.addCell(studentId);
            sheet.addCell(_class);
            for(int j=3;j<recordTimes.size()+3;j++){
                recordList = students.get(i-1).getAbsenceRecords();
                for (Record record:recordList){
                    for(int t=0;t <recordTimes.size();t++){
                        if (record.getRecordTime().getTime().getTime()==recordTimes.get(t).getTime().getTime()){
                            if(record.getAbsenceType() == absenceType.ABSENCE.toInteger()){
                                DebugLog.e(record.getRecordTime().getTime().toString());
                                label = new Label(t+3,i,"X");
                                sum_counter++;
                            }else if (record.getAbsenceType() == absenceType.VACATE.toInteger()){
                                label = new Label(t+3,i,"I");
                            }else{
                                label = new Label(t+3,i,"V");
                            }
                            sheet.addCell(label);
                        }
                    }
                }
            }
            sum = new Label(recordTimes.size()+3,i,sum_counter+"");
            sheet.addCell(sum);
        }
    }

    private void createSheetHead(WritableSheet sheet, Context context) throws WriteException {
        if (sheet == null) return;
        DebugLog.e("createSheetHead");
        Label label1 = new Label(0,0,  context.getString(R.string.sheet_name));
        Label label2 = new Label(1,0,  context.getString(R.string.sheet_studentId));
        Label label3 = new Label(2,0,  context.getString(R.string.sheet_class));
        sheet.addCell(label1);
        sheet.addCell(label2);
        sheet.addCell(label3);
        Label time = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        List<RecordTime> recordTimes = DbHelper.getInstance().getAllRecordTimeAscOrdered();
        int countTimes = recordTimes.size();
        for (int j =3; j < countTimes+3; j++){
            time = new Label(j,0, dateFormat.format(recordTimes.get(j-3).getTime()));
            sheet.addCell(time);
        }
        Label labelSum = new Label(countTimes+3,0,context.getString(R.string.sheet_sum));
        sheet.addCell(labelSum);
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
