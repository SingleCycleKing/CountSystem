package com.cursor.generator;

import java.util.Date;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;
import de.greenrobot.daogenerator.ToMany;

/**
 * Generator for creating model and helper of database
 * ****************
 * USER: ldx
 * EMAIL: danxionglei@foxmail.com
 * PROJECT_NAME: CountSystem
 * DATE: 2015/3/9
 */
public class DbGenerator {
    public static void main(String[] args)throws Exception{
        Schema schema = new Schema(1, "com.unique.countsystem");
        createStudentAndAbsence(schema);
        new DaoGenerator().generateAll(schema,"app/src-gen/");
    }

    private static void createStudentAndAbsence(Schema schema){
        Entity student = schema.addEntity("student");
        student.addIdProperty().autoincrement().primaryKey();
        student.addStringProperty("name");
        Property studentId= student.addIntProperty("studentId").notNull().getProperty();
        student.addIntProperty("_class");

        Entity absenceRecord = schema.addEntity("record");
        absenceRecord.addIdProperty().autoincrement().primaryKey();
        absenceRecord.addIntProperty("date");
        absenceRecord.addIntProperty("absenceType");

        ToMany recordToStudent = student.addToMany(absenceRecord, studentId);
        recordToStudent.setName("AbsenceRecords");
        recordToStudent.orderDesc();
    }
}
