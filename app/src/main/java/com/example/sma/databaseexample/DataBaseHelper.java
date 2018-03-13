package com.example.sma.databaseexample;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by sma on 11.03.2016.
 */
public class DataBaseHelper extends SQLiteOpenHelper implements BaseColumns {

    public  static final String DATABASE_NAME = "mymoney.db";
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_TABLE = "money";
    public static final String NAME_COLUMN = "name";
    public static final String DEBT_OR_CREDIT_COLUMN = "debt_or_credit";
    public static final String MONEY_COLUMN = "money";
    public static final String WHEN_COLUMN = "date";

    public static final String DATABASE_CREATE_SCRIPT = "create table " + DATABASE_TABLE +
                                " ("+BaseColumns._ID + " integer primary key autoincrement, "+
                                NAME_COLUMN + " text not null, "+
                                DEBT_OR_CREDIT_COLUMN + " integer not null default 0, "+
                                MONEY_COLUMN + " real, " + WHEN_COLUMN + " text default \"2016-01-01\");";

    public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public  DataBaseHelper (Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DATABASE_CREATE_SCRIPT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF IT EXIST"+DATABASE_TABLE);
        onCreate(sqLiteDatabase);

    }
}
