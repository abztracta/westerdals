package no.woact.pg4600.assignment2.ronesp13.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class NameDAO {

    public static final String TABLE_NAME = "names";
    public static final String NAME_ATTR = "name";

    private Context context;
    private DatabaseHelper helper;
    private SQLiteDatabase database;

    public NameDAO(Context context) {
        this.context = context;
    }

    public NameDAO open() {
        helper = new DatabaseHelper(context);
        database = helper.getWritableDatabase();
        return this;
    }

    public Cursor getAll() {
        return database.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    public void close() {
        database.close();
    }
}
