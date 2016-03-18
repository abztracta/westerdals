package no.woact.pg4600.assignment2.ronesp13.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class HighscoreDAO {

    public static final String TABLE_NAME = "highscores";
    public static final String NAME_ATTR = "name";
    public static final String SCORE_ATTR = "score";
    private static final String SELECT_SQL = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + SCORE_ATTR + " DESC";

    private Context context;
    private DatabaseHelper helper;
    private SQLiteDatabase database;

    public HighscoreDAO(Context context) {
        this.context = context;
    }

    public HighscoreDAO open() {
        helper = new DatabaseHelper(context);
        database = helper.getWritableDatabase();
        return this;
    }

    public void addHighscore(String name, int score) {
        ContentValues values = new ContentValues();
        values.put(NAME_ATTR, name);
        values.put(SCORE_ATTR, score);
        database.insert(TABLE_NAME, null, values);
    }

    public Cursor getAll() {
        return database.rawQuery(SELECT_SQL, null);
    }

    public void close() {
        database.close();
    }
}
