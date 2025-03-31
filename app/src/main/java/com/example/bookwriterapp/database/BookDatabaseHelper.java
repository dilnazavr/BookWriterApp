package com.example.bookwriterapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BookDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "books.db";
    private static final int DATABASE_VERSION = 1;

    public BookDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE books (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "title TEXT NOT NULL, " +
                "last_edited DATETIME DEFAULT CURRENT_TIMESTAMP)");

        db.execSQL("CREATE TABLE chapters (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "book_id INTEGER NOT NULL, " +
                "title TEXT NOT NULL, " +
                "content TEXT DEFAULT '', " +
                "last_edited DATETIME DEFAULT CURRENT_TIMESTAMP, " +
                "FOREIGN KEY (book_id) REFERENCES books (id) ON DELETE CASCADE)");

        db.execSQL("CREATE TABLE notes (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "book_id INTEGER NOT NULL, " +
                "title TEXT NOT NULL, " +
                "content TEXT DEFAULT '', " +
                "category TEXT CHECK(category IN ('Общие идеи', 'Мир', 'Персонажи', 'Чек-лист')), " +
                "FOREIGN KEY (book_id) REFERENCES books (id) ON DELETE CASCADE)");

        db.execSQL("CREATE TABLE progress (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "book_id INTEGER NOT NULL, " +
                "date DATE DEFAULT CURRENT_DATE, " +
                "words_written INTEGER DEFAULT 0, " +
                "FOREIGN KEY (book_id) REFERENCES books (id) ON DELETE CASCADE)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS books");
        db.execSQL("DROP TABLE IF EXISTS chapters");
        db.execSQL("DROP TABLE IF EXISTS notes");
        db.execSQL("DROP TABLE IF EXISTS progress");
        onCreate(db);
    }
}
