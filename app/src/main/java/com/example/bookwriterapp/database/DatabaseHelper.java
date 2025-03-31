package com.example.bookwriterapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.bookwriterapp.model.Book;
import com.example.bookwriterapp.model.Chapter;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "book_writer_app.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_BOOKS = "books";
    private static final String TABLE_CHAPTERS = "chapters";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createBooksTable = "CREATE TABLE " + TABLE_BOOKS + " (id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT NOT NULL, last_edited DATETIME DEFAULT CURRENT_TIMESTAMP)";
        db.execSQL(createBooksTable);

        String createChaptersTable = "CREATE TABLE " + TABLE_CHAPTERS + " (id INTEGER PRIMARY KEY AUTOINCREMENT, book_id INTEGER NOT NULL, title TEXT NOT NULL, content TEXT DEFAULT '', last_edited DATETIME DEFAULT CURRENT_TIMESTAMP, FOREIGN KEY (book_id) REFERENCES books (id) ON DELETE CASCADE)";
        db.execSQL(createChaptersTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHAPTERS);
        onCreate(db);
    }

    // Метод для получения главы по ID
    public Chapter getChapterById(int chapterId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CHAPTERS + " WHERE id = ?", new String[]{String.valueOf(chapterId)});

        Chapter chapter = null;
        if (cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
            String content = cursor.getString(cursor.getColumnIndexOrThrow("content"));
            chapter = new Chapter(id, title, content);
        }
        cursor.close();
        db.close();
        return chapter;
    }

    // Метод для получения всех глав книги по ID
    public List<Chapter> getChaptersByBookId(int bookId) {
        List<Chapter> chapters = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CHAPTERS + " WHERE book_id = ?", new String[]{String.valueOf(bookId)});

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
                String content = cursor.getString(cursor.getColumnIndexOrThrow("content"));
                Chapter chapter = new Chapter(id, title, content);
                chapters.add(chapter);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return chapters;
    }

    // Метод для обновления главы
    public boolean updateChapter(int chapterId, String title, String content) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", title);
        values.put("content", content);

        int rowsAffected = db.update(TABLE_CHAPTERS, values, "id = ?", new String[]{String.valueOf(chapterId)});
        db.close();
        return rowsAffected > 0;
    }

    public boolean addBook(String title) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", title);

        long result = db.insert(TABLE_BOOKS, null, values);
        db.close();
        return result != -1;
    }

    public boolean addChapter(int bookId, String title, String content) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("book_id", bookId);
        values.put("title", title);
        values.put("content", content);

        long result = db.insert(TABLE_CHAPTERS, null, values);
        db.close();
        return result != -1;
    }

    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM books", null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String title = cursor.getString(cursor.getColumnIndex("title"));
                String lastEdited = cursor.getString(cursor.getColumnIndex("last_edited"));
                books.add(new Book(id, title, lastEdited));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return books;
    }
}
