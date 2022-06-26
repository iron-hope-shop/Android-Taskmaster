package com.example.projecttwo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.TableLayout;

import java.util.ArrayList;
import java.util.List;

public class EventController extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;

    private static final String DATABASE_NAME = "Events.db";

    private static final String TABLE_EVENT = "event";

    private static final String COLUMN_EVENT_ID = "event_id";
    private static final String COLUMN_NAME = "event_name";
    private static final String COLUMN_DATETIME = "event_datetime";
    private static final String COLUMN_DESCRIPTION = "event_description";
    private EventModel dummyEvent;

    private String CREATE_EVENT_TABLE = "CREATE TABLE " + TABLE_EVENT + "("
            + COLUMN_EVENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_NAME + " TEXT," + COLUMN_DATETIME + " TEXT,"
            + COLUMN_DESCRIPTION + " TEXT" + ")";

    private String DROP_EVENT_TABLE = "DROP TABLE IF EXISTS " + TABLE_EVENT;

    public EventController(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_EVENT_TABLE);

        dummyEvent = new EventModel();
        dummyEvent.setName("dummy event");
        dummyEvent.setDatetime("10/22/2022@4:36pm");
        dummyEvent.setDescription("This is a dummy event");

        this.addEvent(dummyEvent);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(DROP_EVENT_TABLE);

        onCreate(db);

    }

    public void addEvent(EventModel event) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, event.getName());
        values.put(COLUMN_DATETIME, event.getDatetime());
        values.put(COLUMN_DESCRIPTION, event.getDescription());

        db.insert(TABLE_EVENT, null, values);
        db.close();
    }

    public List<EventModel> getAllEvents() {
        String[] columns = {
                COLUMN_EVENT_ID,
                COLUMN_NAME,
                COLUMN_DATETIME,
                COLUMN_DESCRIPTION
        };
        String sortOrder =
                COLUMN_NAME + " ASC";
        List<EventModel> eventList = new ArrayList<EventModel>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_EVENT, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order


        if (cursor.moveToFirst()) {
            do {
                EventModel event = new EventModel();
                event.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_EVENT_ID))));
                event.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
                event.setDatetime(cursor.getString(cursor.getColumnIndex(COLUMN_DATETIME)));
                event.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)));
                eventList.add(event);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return eventList;
    }

    public void updateEvent(EventModel event) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, event.getName());
        values.put(COLUMN_DATETIME, event.getDatetime());
        values.put(COLUMN_DESCRIPTION, event.getDescription());

        db.update(TABLE_EVENT, values, COLUMN_EVENT_ID + " = ?",
                new String[]{String.valueOf(event.getId())});
        db.close();
    }

    public void deleteEvent(EventModel event) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_EVENT, COLUMN_EVENT_ID + " = ?",
                new String[]{String.valueOf(event.getId())});
        db.close();
    }

    public boolean checkEvent(String email) {

        String[] columns = {
                COLUMN_EVENT_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = COLUMN_NAME + " = ?";

        String[] selectionArgs = {email};

        Cursor cursor = db.query(TABLE_EVENT, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }

        return false;
    }

    public boolean checkEvent(String name, String datetime) {

        String[] columns = {
                COLUMN_EVENT_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = COLUMN_NAME + " = ?" + " AND " + COLUMN_DATETIME + " = ?";

        String[] selectionArgs = {name, datetime};

        Cursor cursor = db.query(TABLE_EVENT, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order

        int cursorCount = cursor.getCount();

        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }

        return false;
    }
}
