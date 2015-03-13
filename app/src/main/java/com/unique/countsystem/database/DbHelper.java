package com.unique.countsystem.database;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.Nullable;

import com.unique.countsystem.DaoMaster;
import com.unique.countsystem.DaoSession;
import com.unique.countsystem.Record;
import com.unique.countsystem.RecordDao;
import com.unique.countsystem.RecordTime;
import com.unique.countsystem.RecordTimeDao;
import com.unique.countsystem.Student;
import com.unique.countsystem.StudentDao;
import com.unique.countsystem.database.model.absenceType;
import com.unique.countsystem.utils.DayUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

/**
 * DatabaseHelper
 * *****************
 * USER: ldx
 * EMAIL: danxionglei@foxmail.com
 * PROJECT_NAME: CountSystem
 * DATE: 2015/3/9
 */
public class DbHelper {
    private static final String TAG = "DBHelper";
    private static final String DB_NAME = "countSystem";

    // FIELD ===========================================================

    //Handlers of database
    private static DaoMaster daoMaster;
    private static DaoSession daoSession;

    //Singleton instance
    private static DbHelper sInstance;

    // data access objects
    private RecordDao mRecordDao;
    private StudentDao mStudentDao;
    private RecordTimeDao mRecordTimeDao;

    /**
     * Context of applications
     * Please ensure using {@link Context#getApplicationContext()}
     */
    private static Context appContext;


    // PUBLIC METHODS ==================================================

    /**
     * Open the Session of database
     * Must do it.Please do it in work thread.
     * @param context context
     */
    public static void preInitHelper(Context context){
        getDaoSession(context);
        getInstance();
        appContext = context;
    }

    //Singleton method
    public static DbHelper getInstance() {
        if (sInstance == null){
            synchronized (DbHelper.class) {
                if (null == sInstance) {
                    sInstance = new DbHelper();
                    sInstance.mRecordDao = daoSession.getRecordDao();
                    sInstance.mStudentDao = daoSession.getStudentDao();
                    sInstance.mRecordTimeDao = daoSession.getRecordTimeDao();
                }
            }
        }
        return sInstance;
    }

    /**
     * Insert or update students list in database
     * @param students models
     */
    public void insertStudentsList(List<Student> students){
        if (students == null||students.isEmpty()){
            return;
        }
        mStudentDao.insertOrReplaceInTx(students);
    }

    /**
     * Insert or update single student model in database
     * @param aStudent single student
     */
    public void insertOrReplaceStudent(Student aStudent){
        mStudentDao.insertOrReplace(aStudent);
    }

    /**
     * Delete a single student
     * @param aStudent student
     */
    public void deleteStudent(Student aStudent){
        mStudentDao.delete(aStudent);
    }

    /**
     * Delete many students
     * @param students students should be deleted
     */
    public void deleteStudent(Student... students){
        mStudentDao.deleteInTx(students);
    }


    /**
     * Delete a single absence record
     * @param aRecord single absence record
     */
    public void deleteAbsenceRecord(Record aRecord){
        mRecordDao.delete(aRecord);
        aRecord.getStudent().resetAbsenceRecords();
    }

    /**
     * Delete many records
     * @param records students should be deleted
     */
    public void deleteAbsenceRecord(List<Record> records){
        mRecordDao.deleteInTx(records);
    }


    /**
     * Load all student records in database
     * @return list contains all students
     */
    public List<Student> getAllStudents(){
        return mStudentDao.loadAll();
    }

    /**
     * Find a specific student with studentId
     * @param studentId specific studentId , like "U201317485"
     * @return student model or null if not matching a specific student
     */
    @Nullable
    public Student getStudentByStudentId(String studentId) throws IllegalArgumentException{
        if (!parseStudentIdFromString(studentId)){
            throw new IllegalArgumentException("StudentId is illegal");
        }
        return mStudentDao.queryBuilder().where(com.unique.countsystem.StudentDao.Properties.StudentId.eq(studentId)).unique();
    }

    /**
     * List all absence records
     * @return list of records
     */
    public List<Record> getAllAbsenceRecords(){
        return mRecordDao.loadAll();
    }

    /**
     * List all absence records from day
     * @param date java.util.Date
     * @return The Absence records list on that day
     * @exception java.lang.IllegalArgumentException if day is incorrect.
     */
    public List<RecordTime> getAbsenceRecordsByDay(Date date){
        return mRecordTimeDao.queryBuilder().where(RecordTimeDao.Properties.Time.eq(date)).list();
    }

    /**
     * List all absence records for a specific student from studentId
     * @param studentId String studentId, like "U201317485"
     * @return list Record. return null when can't find a specific student
     * @exception java.lang.IllegalArgumentException if studentId is illegal
     */
    @Nullable
    public List<Record> getAbsenceRecordsForStudent(String studentId) throws IllegalArgumentException{
        Student student = getStudentByStudentId(studentId);
        if (student == null){
            return null;
        }
        return student.getAbsenceRecords();
    }

    public void insertOrReplaceAbsenceRecord(Record record,Student student){
        mRecordDao.insertOrReplace(record);
        student.resetAbsenceRecords();
    }


    /**
     * Create student model instance
     *
     * @param name student's name
     * @param studentId student's id "U201317485"
     * @param _class student's class
     * @return student instance
     */
    public static Student createStudentModel(String name, String studentId, String _class)
            throws IllegalArgumentException{
        if (parseStudentIdFromString(studentId)){
            throw new IllegalArgumentException("studentId is illegal");
        }
        _class = _class.trim();
        check_class(_class);
        return new Student(null, name, studentId, _class);
    }

    /**
     * Create model of absence record
     *
     * @param type {@link absenceType}
     * @param student Student by getStudent() in database
     * @return record instance
     * @throws java.lang.IllegalArgumentException DayUtils.fromString(date)
     */
    public Record createAbsenceRecordModel(absenceType type, Student student, RecordTime recordTime) throws IllegalArgumentException{
        return new Record(null, type.toInteger(), student.getId(), recordTime.getId());
    }

    public void insertOrReplaceRecordTime(Date date){
        mRecordTimeDao.insertOrReplace(new RecordTime(null,date));
    }

    public long getSumCountRecordTime(){
        return mRecordTimeDao.count();
    }

    public long getSumCountRecord(){
        return mRecordDao.count();
    }

    public List<RecordTime> getAllRecordTime(){
        return mRecordTimeDao.loadAll();
    }


    /**
     * @return sum count of students
     */
    public long getSumCountOfStudents(){
        return mStudentDao.count();
    }

    /**
     * @return sum count times
     */
//    public int getSumCountTimes(){
//        String DISTINCT_DATE_FROM_RECORD = "SELECT DISTINCT " + RecordDao.Properties.Date.columnName + " FROM " + com.unique.countsystem.RecordDao.TABLENAME;
//        return getDaoSession(appContext).getDatabase().rawQuery(DISTINCT_DATE_FROM_RECORD, null).getCount();
//        if (getSumCountList() == null)
//            return 0;
//        return getSumCountList().size();
//    }

//    @Nullable
//    public List<Integer> getSumCountList(){
//        String DISTINCT_DATE_FROM_RECORD = "SELECT DISTINCT " + RecordDao.Properties.Date.columnName + " FROM " + com.unique.countsystem.RecordDao.TABLENAME;
//        Cursor cursor = getDaoSession(appContext).getDatabase().rawQuery(DISTINCT_DATE_FROM_RECORD, null);
//        List<Integer> result = new ArrayList<>(100);
//        if(cursor.moveToFirst()){
//            do{
//                result.add(cursor.getInt(cursor.getColumnIndex(RecordDao.Properties.Date.columnName)));
//            }while (cursor.moveToNext());
//            return  result;
//        }
//        return null;
//    }

    /**
     * use studentId
     * @param studentId U201317485
     * @return int 0 if not found
     */
    public int getSumAbsenceTimesOfAStudent(String studentId)throws IllegalArgumentException{
        Student student = getStudentByStudentId(studentId);
        if (null==student){
            return 0;
        }
        return student.getAbsenceRecords().size();
    }

    // PRIVATE METHODS =================================================

    /**
     * forbid the new DbHelper()
     */
    private DbHelper(){

    }

    private static boolean parseStudentIdFromString(String studentId) {
        Pattern pattern = Pattern.compile("^(U|u)20[0-9]{7}$");
        return pattern.matcher(studentId).matches();
    }


    /**
     * Insert or replace absence records list;
     * @param records absence record list
     */
    private void insertOrReplaceAbsenceRecord(List<Record> records){
        if (records==null||records.isEmpty()){
            return;
        }
        mRecordDao.insertOrReplaceInTx(records);
    }

    /**
     * Could be added
     * @param _class
     * @return
     */
    private static boolean check_class(String _class) throws IllegalArgumentException{
        return true;
    }

    /**
     * 取得DaoMaster
     *
     * @param context context
     * @return master
     */
    private static DaoMaster getDaoMaster(Context context) {
        if (daoMaster == null) {
            DaoMaster.OpenHelper helper = new DaoMaster.DevOpenHelper(context, DB_NAME, null);
            daoMaster = new DaoMaster(helper.getWritableDatabase());
        }
        return daoMaster;
    }

    /**
     * 取得DaoSession
     *
     * @param context context
     * @return session
     */
    private static DaoSession getDaoSession(Context context) {
        if (daoSession == null) {
            if (daoMaster == null) {
                daoMaster = getDaoMaster(context);
            }
            daoSession = daoMaster.newSession();
        }
        return daoSession;
    }
}
