package com.chailijun.mtime.db.gen;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.chailijun.mtime.db.entity.AdvList;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "ADV_LIST".
*/
public class AdvListDao extends AbstractDao<AdvList, Long> {

    public static final String TABLENAME = "ADV_LIST";

    /**
     * Properties of entity AdvList.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Type = new Property(1, String.class, "type", false, "TYPE");
        public final static Property TypeName = new Property(2, String.class, "typeName", false, "TYPE_NAME");
        public final static Property AdvTag = new Property(3, String.class, "advTag", false, "ADV_TAG");
        public final static Property IsHorizontalScreen = new Property(4, boolean.class, "isHorizontalScreen", false, "IS_HORIZONTAL_SCREEN");
        public final static Property StartDate = new Property(5, long.class, "startDate", false, "START_DATE");
        public final static Property EndDate = new Property(6, long.class, "endDate", false, "END_DATE");
        public final static Property Url = new Property(7, String.class, "url", false, "URL");
        public final static Property Image = new Property(8, String.class, "image", false, "IMAGE");
        public final static Property Tag = new Property(9, String.class, "tag", false, "TAG");
        public final static Property IsOpenH5 = new Property(10, boolean.class, "isOpenH5", false, "IS_OPEN_H5");
        public final static Property IsLogo = new Property(11, boolean.class, "isLogo", false, "IS_LOGO");
        public final static Property EntryText = new Property(12, String.class, "entryText", false, "ENTRY_TEXT");
        public final static Property GotoPage_gotoType = new Property(13, String.class, "gotoPage_gotoType", false, "GOTO_PAGE_GOTO_TYPE");
        public final static Property GotoPage_url = new Property(14, String.class, "gotoPage_url", false, "GOTO_PAGE_URL");
        public final static Property GotoPage_param_movieId = new Property(15, int.class, "gotoPage_param_movieId", false, "GOTO_PAGE_PARAM_MOVIE_ID");
        public final static Property GotoPage_param1_movieId = new Property(16, String.class, "gotoPage_param1_movieId", false, "GOTO_PAGE_PARAM1_MOVIE_ID");
        public final static Property GotoPage_isGoH5 = new Property(17, boolean.class, "gotoPage_isGoH5", false, "GOTO_PAGE_IS_GO_H5");
        public final static Property GotoPage_relatedTypeUrl = new Property(18, String.class, "gotoPage_relatedTypeUrl", false, "GOTO_PAGE_RELATED_TYPE_URL");
    };


    public AdvListDao(DaoConfig config) {
        super(config);
    }
    
    public AdvListDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"ADV_LIST\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"TYPE\" TEXT," + // 1: type
                "\"TYPE_NAME\" TEXT," + // 2: typeName
                "\"ADV_TAG\" TEXT," + // 3: advTag
                "\"IS_HORIZONTAL_SCREEN\" INTEGER NOT NULL ," + // 4: isHorizontalScreen
                "\"START_DATE\" INTEGER NOT NULL ," + // 5: startDate
                "\"END_DATE\" INTEGER NOT NULL ," + // 6: endDate
                "\"URL\" TEXT," + // 7: url
                "\"IMAGE\" TEXT," + // 8: image
                "\"TAG\" TEXT," + // 9: tag
                "\"IS_OPEN_H5\" INTEGER NOT NULL ," + // 10: isOpenH5
                "\"IS_LOGO\" INTEGER NOT NULL ," + // 11: isLogo
                "\"ENTRY_TEXT\" TEXT," + // 12: entryText
                "\"GOTO_PAGE_GOTO_TYPE\" TEXT," + // 13: gotoPage_gotoType
                "\"GOTO_PAGE_URL\" TEXT," + // 14: gotoPage_url
                "\"GOTO_PAGE_PARAM_MOVIE_ID\" INTEGER NOT NULL ," + // 15: gotoPage_param_movieId
                "\"GOTO_PAGE_PARAM1_MOVIE_ID\" TEXT," + // 16: gotoPage_param1_movieId
                "\"GOTO_PAGE_IS_GO_H5\" INTEGER NOT NULL ," + // 17: gotoPage_isGoH5
                "\"GOTO_PAGE_RELATED_TYPE_URL\" TEXT);"); // 18: gotoPage_relatedTypeUrl
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"ADV_LIST\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, AdvList entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String type = entity.getType();
        if (type != null) {
            stmt.bindString(2, type);
        }
 
        String typeName = entity.getTypeName();
        if (typeName != null) {
            stmt.bindString(3, typeName);
        }
 
        String advTag = entity.getAdvTag();
        if (advTag != null) {
            stmt.bindString(4, advTag);
        }
        stmt.bindLong(5, entity.getIsHorizontalScreen() ? 1L: 0L);
        stmt.bindLong(6, entity.getStartDate());
        stmt.bindLong(7, entity.getEndDate());
 
        String url = entity.getUrl();
        if (url != null) {
            stmt.bindString(8, url);
        }
 
        String image = entity.getImage();
        if (image != null) {
            stmt.bindString(9, image);
        }
 
        String tag = entity.getTag();
        if (tag != null) {
            stmt.bindString(10, tag);
        }
        stmt.bindLong(11, entity.getIsOpenH5() ? 1L: 0L);
        stmt.bindLong(12, entity.getIsLogo() ? 1L: 0L);
 
        String entryText = entity.getEntryText();
        if (entryText != null) {
            stmt.bindString(13, entryText);
        }
 
        String gotoPage_gotoType = entity.getGotoPage_gotoType();
        if (gotoPage_gotoType != null) {
            stmt.bindString(14, gotoPage_gotoType);
        }
 
        String gotoPage_url = entity.getGotoPage_url();
        if (gotoPage_url != null) {
            stmt.bindString(15, gotoPage_url);
        }
        stmt.bindLong(16, entity.getGotoPage_param_movieId());
 
        String gotoPage_param1_movieId = entity.getGotoPage_param1_movieId();
        if (gotoPage_param1_movieId != null) {
            stmt.bindString(17, gotoPage_param1_movieId);
        }
        stmt.bindLong(18, entity.getGotoPage_isGoH5() ? 1L: 0L);
 
        String gotoPage_relatedTypeUrl = entity.getGotoPage_relatedTypeUrl();
        if (gotoPage_relatedTypeUrl != null) {
            stmt.bindString(19, gotoPage_relatedTypeUrl);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, AdvList entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String type = entity.getType();
        if (type != null) {
            stmt.bindString(2, type);
        }
 
        String typeName = entity.getTypeName();
        if (typeName != null) {
            stmt.bindString(3, typeName);
        }
 
        String advTag = entity.getAdvTag();
        if (advTag != null) {
            stmt.bindString(4, advTag);
        }
        stmt.bindLong(5, entity.getIsHorizontalScreen() ? 1L: 0L);
        stmt.bindLong(6, entity.getStartDate());
        stmt.bindLong(7, entity.getEndDate());
 
        String url = entity.getUrl();
        if (url != null) {
            stmt.bindString(8, url);
        }
 
        String image = entity.getImage();
        if (image != null) {
            stmt.bindString(9, image);
        }
 
        String tag = entity.getTag();
        if (tag != null) {
            stmt.bindString(10, tag);
        }
        stmt.bindLong(11, entity.getIsOpenH5() ? 1L: 0L);
        stmt.bindLong(12, entity.getIsLogo() ? 1L: 0L);
 
        String entryText = entity.getEntryText();
        if (entryText != null) {
            stmt.bindString(13, entryText);
        }
 
        String gotoPage_gotoType = entity.getGotoPage_gotoType();
        if (gotoPage_gotoType != null) {
            stmt.bindString(14, gotoPage_gotoType);
        }
 
        String gotoPage_url = entity.getGotoPage_url();
        if (gotoPage_url != null) {
            stmt.bindString(15, gotoPage_url);
        }
        stmt.bindLong(16, entity.getGotoPage_param_movieId());
 
        String gotoPage_param1_movieId = entity.getGotoPage_param1_movieId();
        if (gotoPage_param1_movieId != null) {
            stmt.bindString(17, gotoPage_param1_movieId);
        }
        stmt.bindLong(18, entity.getGotoPage_isGoH5() ? 1L: 0L);
 
        String gotoPage_relatedTypeUrl = entity.getGotoPage_relatedTypeUrl();
        if (gotoPage_relatedTypeUrl != null) {
            stmt.bindString(19, gotoPage_relatedTypeUrl);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public AdvList readEntity(Cursor cursor, int offset) {
        AdvList entity = new AdvList( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // type
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // typeName
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // advTag
            cursor.getShort(offset + 4) != 0, // isHorizontalScreen
            cursor.getLong(offset + 5), // startDate
            cursor.getLong(offset + 6), // endDate
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // url
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // image
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // tag
            cursor.getShort(offset + 10) != 0, // isOpenH5
            cursor.getShort(offset + 11) != 0, // isLogo
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // entryText
            cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13), // gotoPage_gotoType
            cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14), // gotoPage_url
            cursor.getInt(offset + 15), // gotoPage_param_movieId
            cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16), // gotoPage_param1_movieId
            cursor.getShort(offset + 17) != 0, // gotoPage_isGoH5
            cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18) // gotoPage_relatedTypeUrl
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, AdvList entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setType(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setTypeName(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setAdvTag(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setIsHorizontalScreen(cursor.getShort(offset + 4) != 0);
        entity.setStartDate(cursor.getLong(offset + 5));
        entity.setEndDate(cursor.getLong(offset + 6));
        entity.setUrl(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setImage(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setTag(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setIsOpenH5(cursor.getShort(offset + 10) != 0);
        entity.setIsLogo(cursor.getShort(offset + 11) != 0);
        entity.setEntryText(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setGotoPage_gotoType(cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13));
        entity.setGotoPage_url(cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14));
        entity.setGotoPage_param_movieId(cursor.getInt(offset + 15));
        entity.setGotoPage_param1_movieId(cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16));
        entity.setGotoPage_isGoH5(cursor.getShort(offset + 17) != 0);
        entity.setGotoPage_relatedTypeUrl(cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(AdvList entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(AdvList entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
