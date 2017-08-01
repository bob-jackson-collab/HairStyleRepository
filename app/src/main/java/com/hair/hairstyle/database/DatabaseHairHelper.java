package com.hair.hairstyle.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.hair.hairstyle.database.table.User;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class DatabaseHairHelper extends OrmLiteSqliteOpenHelper {
    private static final String Database_NAME = "hairstyle.db";

    private Map<String, Dao> mDao = new HashMap<String, Dao>();

    private static DatabaseHairHelper instance;

    private DatabaseHairHelper(Context context) {
        super(context, Database_NAME, null, 1);
    }

    /**
     * 单例获取该Helper
     *
     * @param context
     * @return
     */
    public static synchronized DatabaseHairHelper getHelper(Context context) {
        context = context.getApplicationContext();
        if (instance == null) {
            synchronized (DatabaseHairHelper.class) {
                if (instance == null)
                    instance = new DatabaseHairHelper(context);
            }
        }
        return instance;
    }

    public synchronized Dao getDao(Class clazz) throws SQLException {
        Dao dao = null;
        String className = clazz.getSimpleName();
        if (mDao.containsKey(className)) {
            dao = mDao.get(className);
        }
        if (dao == null) {
            dao = super.getDao(clazz);
            mDao.put(className, dao);
        }
        return dao;
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, User.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, User.class, true);
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 释放资源
     */
    @Override
    public void close() {
        super.close();
        for (String key : mDao.keySet()) {
            Dao dao = mDao.get(key);
            dao = null;
        }
    }

}  