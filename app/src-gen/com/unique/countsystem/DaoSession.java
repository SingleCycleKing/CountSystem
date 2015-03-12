package com.unique.countsystem;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

import com.unique.countsystem.Student;
import com.unique.countsystem.Record;

import com.unique.countsystem.StudentDao;
import com.unique.countsystem.RecordDao;


/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig studentDaoConfig;
    private final DaoConfig recordDaoConfig;

    private final StudentDao studentDao;
    private final RecordDao recordDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        studentDaoConfig = daoConfigMap.get(StudentDao.class).clone();
        studentDaoConfig.initIdentityScope(type);

        recordDaoConfig = daoConfigMap.get(RecordDao.class).clone();
        recordDaoConfig.initIdentityScope(type);

        studentDao = new StudentDao(studentDaoConfig, this);
        recordDao = new RecordDao(recordDaoConfig, this);

        registerDao(Student.class, studentDao);
        registerDao(Record.class, recordDao);
    }
    
    public void clear() {
        studentDaoConfig.getIdentityScope().clear();
        recordDaoConfig.getIdentityScope().clear();
    }

    public StudentDao getStudentDao() {
        return studentDao;
    }

    public RecordDao getRecordDao() {
        return recordDao;
    }

}
