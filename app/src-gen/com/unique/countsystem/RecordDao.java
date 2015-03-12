package com.unique.countsystem;

import java.util.List;
import java.util.ArrayList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.SqlUtils;
import de.greenrobot.dao.internal.DaoConfig;
import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;

import com.unique.countsystem.Record;

/**
 * DAO for table RECORD.
*/
public class RecordDao extends AbstractDao<Record, Long> {

    public static final String TABLENAME = "RECORD";

    /**
     * Properties of entity Record.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Date = new Property(1, Integer.class, "date", false, "DATE");
        public final static Property AbsenceType = new Property(2, Integer.class, "absenceType", false, "ABSENCE_TYPE");
        public final static Property SssId = new Property(3, long.class, "sssId", false, "SSS_ID");
    };

    private DaoSession daoSession;

    private Query<Record> student_AbsenceRecordsQuery;

    public RecordDao(DaoConfig config) {
        super(config);
    }
    
    public RecordDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'RECORD' (" + //
                "'_id' INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "'DATE' INTEGER," + // 1: date
                "'ABSENCE_TYPE' INTEGER," + // 2: absenceType
                "'SSS_ID' INTEGER NOT NULL );"); // 3: sssId
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'RECORD'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Record entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        Integer date = entity.getDate();
        if (date != null) {
            stmt.bindLong(2, date);
        }
 
        Integer absenceType = entity.getAbsenceType();
        if (absenceType != null) {
            stmt.bindLong(3, absenceType);
        }
        stmt.bindLong(4, entity.getSssId());
    }

    @Override
    protected void attachEntity(Record entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public Record readEntity(Cursor cursor, int offset) {
        Record entity = new Record( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getInt(offset + 1), // date
            cursor.isNull(offset + 2) ? null : cursor.getInt(offset + 2), // absenceType
            cursor.getLong(offset + 3) // sssId
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Record entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setDate(cursor.isNull(offset + 1) ? null : cursor.getInt(offset + 1));
        entity.setAbsenceType(cursor.isNull(offset + 2) ? null : cursor.getInt(offset + 2));
        entity.setSssId(cursor.getLong(offset + 3));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Record entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(Record entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
    /** Internal query to resolve the "AbsenceRecords" to-many relationship of Student. */
    public List<Record> _queryStudent_AbsenceRecords(long sssId) {
        synchronized (this) {
            if (student_AbsenceRecordsQuery == null) {
                QueryBuilder<Record> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.SssId.eq(null));
                student_AbsenceRecordsQuery = queryBuilder.build();
            }
        }
        Query<Record> query = student_AbsenceRecordsQuery.forCurrentThread();
        query.setParameter(0, sssId);
        return query.list();
    }

    private String selectDeep;

    protected String getSelectDeep() {
        if (selectDeep == null) {
            StringBuilder builder = new StringBuilder("SELECT ");
            SqlUtils.appendColumns(builder, "T", getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T0", daoSession.getStudentDao().getAllColumns());
            builder.append(" FROM RECORD T");
            builder.append(" LEFT JOIN STUDENT T0 ON T.'SSS_ID'=T0.'_id'");
            builder.append(' ');
            selectDeep = builder.toString();
        }
        return selectDeep;
    }
    
    protected Record loadCurrentDeep(Cursor cursor, boolean lock) {
        Record entity = loadCurrent(cursor, 0, lock);
        int offset = getAllColumns().length;

        Student student = loadCurrentOther(daoSession.getStudentDao(), cursor, offset);
         if(student != null) {
            entity.setStudent(student);
        }

        return entity;    
    }

    public Record loadDeep(Long key) {
        assertSinglePk();
        if (key == null) {
            return null;
        }

        StringBuilder builder = new StringBuilder(getSelectDeep());
        builder.append("WHERE ");
        SqlUtils.appendColumnsEqValue(builder, "T", getPkColumns());
        String sql = builder.toString();
        
        String[] keyArray = new String[] { key.toString() };
        Cursor cursor = db.rawQuery(sql, keyArray);
        
        try {
            boolean available = cursor.moveToFirst();
            if (!available) {
                return null;
            } else if (!cursor.isLast()) {
                throw new IllegalStateException("Expected unique result, but count was " + cursor.getCount());
            }
            return loadCurrentDeep(cursor, true);
        } finally {
            cursor.close();
        }
    }
    
    /** Reads all available rows from the given cursor and returns a list of new ImageTO objects. */
    public List<Record> loadAllDeepFromCursor(Cursor cursor) {
        int count = cursor.getCount();
        List<Record> list = new ArrayList<Record>(count);
        
        if (cursor.moveToFirst()) {
            if (identityScope != null) {
                identityScope.lock();
                identityScope.reserveRoom(count);
            }
            try {
                do {
                    list.add(loadCurrentDeep(cursor, false));
                } while (cursor.moveToNext());
            } finally {
                if (identityScope != null) {
                    identityScope.unlock();
                }
            }
        }
        return list;
    }
    
    protected List<Record> loadDeepAllAndCloseCursor(Cursor cursor) {
        try {
            return loadAllDeepFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }
    

    /** A raw-style query where you can pass any WHERE clause and arguments. */
    public List<Record> queryDeep(String where, String... selectionArg) {
        Cursor cursor = db.rawQuery(getSelectDeep() + where, selectionArg);
        return loadDeepAllAndCloseCursor(cursor);
    }
 
}
