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
import com.unique.countsystem.utils.DebugLog;

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

    public void insertOrReplaceRecords(List<Record> records){
        mRecordDao.insertOrReplaceInTx(records);
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
        if (!checkStudentId(studentId)){
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


    public long insertOrReplaceAbsenceRecord(Record record,Student student){
        student.getAbsenceRecords().add(record);
        return mRecordDao.insertOrReplace(record);
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
        studentId = studentId.trim();
        if (!checkStudentId(studentId)){
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
    public Record createAbsenceRecordModel(absenceType type, Student student, long recordTimeId) throws IllegalArgumentException{
        return new Record(null, type.toInteger(), student.getId(), recordTimeId);
    }

    public long insertOrReplaceRecordTime(Date date){
        return mRecordTimeDao.insertOrReplace(new RecordTime(null,date));
    }

    public long getSumCountRecordTime(){
        return mRecordTimeDao.count();
    }

    public RecordTime getRecordTimeById(long id){
        return mRecordTimeDao.queryBuilder().where(RecordTimeDao.Properties.Id.eq(id)).unique();
    }

    public long getSumCountRecord(){
        return mRecordDao.count();
    }

    public List<RecordTime> getAllRecordTimeAscOrdered(){
        return mRecordTimeDao.queryBuilder().orderAsc(RecordTimeDao.Properties.Time).list();
    }

    public List<RecordTime> getAllRecordTimeDescOrdered(){
        return mRecordTimeDao.queryBuilder().orderDesc(RecordTimeDao.Properties.Time).list();
    }

    /**
     * @return sum count of students
     */
    public long getSumCountOfStudents(){
        return mStudentDao.count();
    }

    @Nullable
    public List<String> getAllClassList(){
        String DISTINCT_CLASS_FROM_STUDENT = "SELECT DISTINCT " + StudentDao.Properties._class.columnName + " FROM " + StudentDao.TABLENAME;
        Cursor cursor = getDaoSession(appContext).getDatabase().rawQuery(DISTINCT_CLASS_FROM_STUDENT, null);
        List<String> result = new ArrayList<>(100);
        if(cursor.moveToFirst()){
            do{
                result.add(cursor.getString(cursor.getColumnIndex(StudentDao.Properties._class.columnName)));
            }while (cursor.moveToNext());
            return  result;
        }
        return null;
    }

    public List<Student> getAllStudentListWithClass(String _class){
        check_class(_class);
        return mStudentDao.queryBuilder().where(StudentDao.Properties._class.eq(_class)).list();
    }

    public boolean checkIsNullRecordTime(RecordTime recordTime){
        return 0 == recordTime.getAbsenceTimes().size();
    }

    public void deleteRecordTime(RecordTime recordTime){
        mRecordTimeDao.delete(recordTime);
    }

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

    public static boolean checkStudentId(String studentId) {
        Pattern pattern = Pattern.compile("^(U|u)(2|3)0[0-9]{7}$");

        //if want to add some special require,use it.
        //return pattern.matcher(studentId).matches();
        return true ;
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
