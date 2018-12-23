package com.example.selimmouelhi.esprit4launch.DataHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;

public class FeedReaderDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "EspritLunch.db";
    public static final String TABLE_NAME = "Restaurant";
    public static final String COLUMN_NAME_TITLE = "Name";
    public static final String COLUMN_NAME_ID = "rest_id";
    public static final String COLUMN_NAME_IMAGE = "image";
    public static final String COLUMN_NAME_RATING= "rating";
    public static final String COLUMN_NAME_ADRESSE = "adresse";

    public FeedReaderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);

    }

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    _ID + " INTEGER PRIMARY KEY," + COLUMN_NAME_TITLE + " TEXT," +
                    COLUMN_NAME_ID + " INTEGER," + COLUMN_NAME_IMAGE +" TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;
}
