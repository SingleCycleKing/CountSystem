package com.unique.countsystem.database;

import android.content.Context;
import android.database.Cursor;

import com.unique.countsystem.DaoMaster;
import com.unique.countsystem.DaoSession;
import com.unique.countsystem.database.model.absenceType;
import com.unique.countsystem.record;
import com.unique.countsystem.recordDao;
import com.unique.countsystem.student;
import com.unique.countsystem.studentDao;
import com.unique.countsystem.utils.DAYUtils;

import java.util.IllegalFormatException;
import java.util.List;
import java.util.regex.Pattern;

import de.greenrobot.dao.query.QueryBuilder;
import de.greenrobot.dao.query.WhereCondition;

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
    private recordDao recordDao;
    private studentDao studentDao;

    /**
     * Context of applications
     * Please ensure using {@link Context#getApplicationContext()}
     */
    private Context appContext;


    // PUBLIC METHODS ==================================================

    /**
     * Open the Session of database
     * Must do it.Please do it in work thread.
     * @param context context
     */
    public static void preInitHelper(Context context){
        getDaoSession(context);
    }

    //Singleton method
    public static DbHelper getInstance() {
        if (sInstance == null){
            synchronized (DbHelper.class) {
                if (null == sInstance) {
                     sInstance = new DbHelper();
                    if (sInstance.appContext == null){
                        throw new IllegalArgumentException("Must call preInitHelper() before");
                    }
                    sInstance.recordDao = daoSession.getRecordDao();
                    sInstance.studentDao = daoSession.getStudentDao();
                }
            }
        }
        return sInstance;
    }

    /**
     * Insert or update students list in database
     * @param students models
     */
    public void insertStudentsList(List<student> students){
        if (students == null||students.isEmpty()){
            return;
        }
        studentDao.insertOrReplaceInTx(students);
    }

    /**
     * Insert or update single student model in database
     * @param aStudent single student
     */
    public void insertOrReplaceStudent(student aStudent){
        studentDao.insertOrReplace(aStudent);
    }

    /**
     * Delete a single student
     * @param aStudent student
     */
    public void deleteStudent(student aStudent){
        studentDao.delete(aStudent);
    }

    /**
     * Delete many students
     * @param students students should be deleted
     */
    public void deleteStudent(student... students){
        studentDao.deleteInTx(students);
    }

    /**
     * Insert or replace an absence record.
     * @param aRecord absence record
     */
    public void insertOrReplaceAbsenceRecord(record aRecord){
        recordDao.insertOrReplace(aRecord);
    }

    /**
     * Insert or replace absence records list;
     * @param records absence record list
     */
    public void insertOrReplaceAbsenceRecord(List<record> records){
        if (records==null||records.isEmpty()){
            return;
        }
        recordDao.insertOrReplaceInTx(records);
    }

    /**
     * Delete a single absence record
     * @param aRecord single absence record
     */
    public void deleteAbsenceRecord(record aRecord){
        recordDao.delete(aRecord);
    }

    /**
     * Delete many records
     * @param records students should be deleted
     */
    public void deleteStudent(List<record> records){
        recordDao.deleteInTx(records);
    }


    /**
     * Load all student records in database
     * @return list contains all students
     */
    public List<student> getAllStudents(){
        return studentDao.loadAll();
    }

    /**
     * Find a specific student with studentId
     * @param studentId specific studentId , like "U201317485"
     * @return student model or null if not matching a specific student
     */
    public student getStudentByStudentId(String studentId) throws IllegalArgumentException{
        int realStudentId = parseStudentIdFromString(studentId);
        return studentDao.queryBuilder().where(com.unique.countsystem.studentDao.Properties.StudentId.eq(realStudentId)).unique();
    }

    /**
     * List all absence records
     * @return list of records
     */
    public List<record> getAllAbsenceRecords(){
        return recordDao.loadAll();
    }

    /**
     * List all absence records from day
     * @param day pattern should be "20130405"
     * @return The Absence records list on that day
     * @exception java.lang.IllegalArgumentException if day is incorrect.
     */
    public List<record> getAbsenceRecordsByDay(String day){
        int date = DAYUtils.fromString(day);
        return recordDao.queryBuilder().where(com.unique.countsystem.recordDao.Properties.Date.eq(date)).list();
    }

    /**
     * List all absence records for a specific student from studentId
     * @param studentId int studentId, like 201317485 without "U"
     * @return list
     */
    public List<record> getAbsenceRecordsForStudent(int studentId){
        return recordDao._queryStudent_AbsenceRecords(studentId);
    }


    /**
     * Create student model instance
     *
     * @param name student's name
     * @param studentId student's id "U201317485"
     * @param _class student's class
     * @return student instance
     */
    public static student createStudentModel(String name, String studentId, String _class)
            throws IllegalArgumentException{
        int realStudentId = parseStudentIdFromString(studentId);
        _class = _class.trim();
        check_class(_class);
        return new student(null, name, realStudentId, _class);
    }

    /**
     * Create model of absence record
     *
     * @param date {@link com.unique.countsystem.utils.DAYUtils}
     * @param type {@link absenceType}
     * @return record instance
     */
    public static record createAbsenceRecordModel(String date, absenceType type) throws IllegalArgumentException{
        return new record(null,DAYUtils.fromString(date), type.toInteger());
    }

    //总人数 点名次数 某个学生有几次没到

    /**
     * @return sum count of students
     */
    public long getSumCountOfStudents(){
        return studentDao.count();
    }

    /**
     * @return sum count times
     */
    public int getSumCountTimes(String _class)throws IllegalArgumentException{
        check_class(_class);
        String DISTINCT_DATE_FROM_RECORD = "SELECT DISTINCT " + com.unique.countsystem.recordDao.Properties.Date + " FROM " + com.unique.countsystem.recordDao.TABLENAME;
        return getDaoSession(appContext).getDatabase().rawQuery(DISTINCT_DATE_FROM_RECORD, null).getColumnCount();
    }

    // PRIVATE METHODS =================================================

    private static int parseStudentIdFromString(String studentId) throws IllegalArgumentException{
        studentId = studentId.trim();
        Pattern pattern = Pattern.compile("^(U|u)20[0-9]{7}$");
        if (!pattern.matcher(studentId).matches()){
            throw new IllegalArgumentException("studentId format error");
        }
        studentId = studentId.substring(1);
        return Integer.parseInt(studentId);
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
