package no.woact.pg4600.assignment2.ronesp13.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Scanner;

import no.woact.pg4600.assignment2.ronesp13.R;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "kims_game";
    private static final int DATABASE_VERSION = 1;

    private static final String CREATE_NAMES_TABLE_SQL = "CREATE TABLE " + NameDAO.TABLE_NAME + " (" +
            NameDAO.NAME_ATTR + " TEXT" +
            ");";
    private static final String CREATE_HIGHSCORE_TABLE_SQL = "CREATE TABLE " + HighscoreDAO.TABLE_NAME + "(" +
            HighscoreDAO.NAME_ATTR + " TEXT," +
            HighscoreDAO.SCORE_ATTR + " INT" +
            ");";
    private Context context;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    private void seed(SQLiteDatabase db) {
        Scanner scanner = new Scanner(context.getResources().openRawResource(R.raw.names));
        while (scanner.hasNext()) {
            ContentValues values = new ContentValues();
            values.put(NameDAO.NAME_ATTR, scanner.nextLine());
            db.insert(NameDAO.TABLE_NAME, null, values);
        }
        scanner.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_NAMES_TABLE_SQL);
        db.execSQL(CREATE_HIGHSCORE_TABLE_SQL);
        seed(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
