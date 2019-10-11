package com.trangdv.appcontact;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import androidx.annotation.Nullable;

import com.trangdv.appcontact.listeners.OnDatabaseChangedListeners;
import com.trangdv.appcontact.model.Contacts;

public class DatabaseHandler extends SQLiteOpenHelper {

    private Context mContext;
    private static OnDatabaseChangedListeners changedListeners;
    public static final String DATABASE_NAME = "saved_recordings.db";
    private static final int DATABASE_VERSION = 1;

    private static final String COMMA = ",";

    public static abstract class DatabaseItem implements BaseColumns {
        public static final String TABLE_NAME = "saved_contact";

        public static final String COLUMN_NAME_CONTACT_NAME = "contact_name";
        public static final String COLUMN_NAME_CONTACT_PHONE_NUMBER = "phone_number";
        public static final String COLUMN_NAME_TIME_ADDED = "time_added";
    }

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DatabaseItem.TABLE_NAME + " (" +
                    DatabaseItem._ID + " INTEGER PRIMARY KEY " + COMMA +
                    DatabaseItem.COLUMN_NAME_CONTACT_NAME + " TEXT " + COMMA +
                    DatabaseItem.COLUMN_NAME_CONTACT_PHONE_NUMBER + " INTEGER " + COMMA +
                    DatabaseItem.COLUMN_NAME_TIME_ADDED + " INTEGER " + ")";
    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + DatabaseItem.TABLE_NAME;

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addContact(String contactName, String phoneNumber) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseItem.COLUMN_NAME_CONTACT_NAME, contactName);
        values.put(DatabaseItem.COLUMN_NAME_CONTACT_PHONE_NUMBER, phoneNumber);
        values.put(DatabaseItem.COLUMN_NAME_TIME_ADDED, System.currentTimeMillis());
        long rowId = db.insert(DatabaseItem.TABLE_NAME, null, values);

        if (changedListeners != null) {
            changedListeners.onNewDatabaseEntryAdded();
        }

        if (rowId == -1) {
            return false;
        } else return true;
    }

    public Contacts getItemAt(int position) {
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {
                DatabaseItem._ID,
                DatabaseItem.COLUMN_NAME_CONTACT_NAME,
                DatabaseItem.COLUMN_NAME_CONTACT_PHONE_NUMBER,
                DatabaseItem.COLUMN_NAME_TIME_ADDED
        };
        Cursor cursor = db.query(DatabaseItem.TABLE_NAME, projection, null, null, null, null, null);
        if (cursor.moveToPosition(position)) {
            Contacts item = new Contacts();
            item.setmId(cursor.getInt(cursor.getColumnIndex(DatabaseItem._ID)));
            item.setmName(cursor.getString(cursor.getColumnIndex(DatabaseItem.COLUMN_NAME_CONTACT_NAME)));
            item.setmPhoneNumber(cursor.getString(cursor.getColumnIndex(DatabaseItem.COLUMN_NAME_CONTACT_PHONE_NUMBER)));
            cursor.close();
            return item;
        }
        return null;
    }

    public void renameItem(Contacts item, String name, String phoneNumber) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseItem.COLUMN_NAME_CONTACT_NAME, name);
        values.put(DatabaseItem.COLUMN_NAME_CONTACT_PHONE_NUMBER, phoneNumber);
        database.update(DatabaseItem.TABLE_NAME, values, DatabaseItem._ID + "=" + item.getmId(), null);
        if (changedListeners != null) {
            changedListeners.onNewDatabaseEntryRenamed();
        }
    }

    public int getCount() {

        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {DatabaseItem._ID};
        Cursor cursor = db.query(DatabaseItem.TABLE_NAME, projection, null, null, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    public static void setChangedListeners(OnDatabaseChangedListeners databaseChangedListeners) {
        changedListeners = databaseChangedListeners;
    }

}
