package com.framgia.music;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;
import com.framgia.music.model.HistoriesLocation;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.framgia.music.Constants.AND;
import static com.framgia.music.Constants.CONDITION_UPDATE;
import static com.framgia.music.Constants.CREATE_TABLE;
import static com.framgia.music.Constants.DATABASE_NAME;
import static com.framgia.music.Constants.DROP_TABLE_IF_EXISTS;
import static com.framgia.music.Constants.LATITUDE;
import static com.framgia.music.Constants.LONGITUDE;
import static com.framgia.music.Constants.REAL;
import static com.framgia.music.Constants.SELECT_FROM;
import static com.framgia.music.Constants.TABLE_NAME;
import static com.framgia.music.Constants.TIME;

/**
 * Created by trong_tai on 08/02/2018.
 */

public class DBManager extends SQLiteOpenHelper {

    private Context mContext;

    public DBManager(Context context) {
        super(context,DATABASE_NAME,null, Constants.DB_VERSION);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(String.format(Locale.US, "%s %s (%s %s, %s %s, %s %s)",
                CREATE_TABLE,TABLE_NAME,LATITUDE,REAL,LONGITUDE,REAL,
                TIME,REAL));
        Log.e("abc", "create success!");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(String.format(Locale.US, "%s %s", DROP_TABLE_IF_EXISTS,TABLE_NAME));
        onCreate(db);
    }

    public void AddHistoriesLocation(HistoriesLocation historiesLocation) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(LATITUDE,historiesLocation.getLatitide());
        values.put(LONGITUDE,historiesLocation.getLongitude());
        values.put(TIME,historiesLocation.getTime());

        db.insert(TABLE_NAME,null,values);

        db.close();
    }

    public List<HistoriesLocation> GetAllHistoriesLoaction() {
        List<HistoriesLocation> historiesLocationList = new ArrayList<HistoriesLocation>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(String.format(Locale.US,
                "%s %s", SELECT_FROM, TABLE_NAME), null);

        if (cursor.moveToFirst()) {
            do {
                HistoriesLocation historiesLocation = new HistoriesLocation();
                historiesLocation.setLatitide(cursor.getDouble(0));
                historiesLocation.setLongitude(cursor.getDouble(1));
                historiesLocation.setTime(cursor.getLong(2));
                historiesLocationList.add(historiesLocation);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return historiesLocationList;
    }

    public void deleteTable() {
        SQLiteDatabase mSqLiteDatabase = this.getWritableDatabase();
        mSqLiteDatabase.execSQL(String.format("%s %s", Constants.DELETE_FROM, TABLE_NAME));
    }
}
