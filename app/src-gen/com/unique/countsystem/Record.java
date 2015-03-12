package com.unique.countsystem;

import com.unique.countsystem.DaoSession;
import de.greenrobot.dao.DaoException;

/**
 * Entity mapped to table RECORD.
 */
public class Record {

    private Long id;
    private Integer date;
    private Integer absenceType;
    private long sssId;

    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient RecordDao myDao;

    private Student student;
    private Long student__resolvedKey;


    public Record() {
    }

    public Record(Long id) {
        this.id = id;
    }

    public Record(Long id, Integer date, Integer absenceType, long sssId) {
        this.id = id;
        this.date = date;
        this.absenceType = absenceType;
        this.sssId = sssId;
    }

    /** called by internal mechanisms, do not call yourself. */
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getRecordDao() : null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDate() {
        return date;
    }

    public void setDate(Integer date) {
        this.date = date;
    }

    public Integer getAbsenceType() {
        return absenceType;
    }

    public void setAbsenceType(Integer absenceType) {
        this.absenceType = absenceType;
    }

    public long getSssId() {
        return sssId;
    }

    public void setSssId(long sssId) {
        this.sssId = sssId;
    }

    /** To-one relationship, resolved on first access. */
    public Student getStudent() {
        long __key = this.sssId;
        if (student__resolvedKey == null || !student__resolvedKey.equals(__key)) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            StudentDao targetDao = daoSession.getStudentDao();
            Student studentNew = targetDao.load(__key);
            synchronized (this) {
                student = studentNew;
            	student__resolvedKey = __key;
            }
        }
        return student;
    }

    public void setStudent(Student student) {
        if (student == null) {
            throw new DaoException("To-one property 'sssId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.student = student;
            sssId = student.getId();
            student__resolvedKey = sssId;
        }
    }

    /** Convenient call for {@link AbstractDao#delete(Object)}. Entity must attached to an entity context. */
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.delete(this);
    }

    /** Convenient call for {@link AbstractDao#update(Object)}. Entity must attached to an entity context. */
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.update(this);
    }

    /** Convenient call for {@link AbstractDao#refresh(Object)}. Entity must attached to an entity context. */
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.refresh(this);
    }

}
