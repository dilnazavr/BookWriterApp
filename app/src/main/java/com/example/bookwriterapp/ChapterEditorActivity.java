package com.example.bookwriterapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.bookwriterapp.database.DatabaseHelper;
import com.example.bookwriterapp.model.Chapter;

public class ChapterEditorActivity extends AppCompatActivity {
    private EditText edtTitle, edtContent;
    private int bookId, chapterId;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_editor);

        edtTitle = findViewById(R.id.edtTitle);
        edtContent = findViewById(R.id.edtContent);
        db = new DatabaseHelper(this);

        bookId = getIntent().getIntExtra("BOOK_ID", -1);
        chapterId = getIntent().getIntExtra("CHAPTER_ID", -1);

        if (chapterId != -1) {
            // Загрузка данных существующей главы для редактирования
            Chapter chapter = db.getChapterById(chapterId);
            edtTitle.setText(chapter.getTitle());
            edtContent.setText(chapter.getContent());
        }

        findViewById(R.id.btnSave).setOnClickListener(v -> {
            String title = edtTitle.getText().toString();
            String content = edtContent.getText().toString();

            if (chapterId == -1) {
                db.addChapter(bookId, title, content);
                Toast.makeText(this, "Глава добавлена!", Toast.LENGTH_SHORT).show();
            } else {
                db.updateChapter(chapterId, title, content);
                Toast.makeText(this, "Глава обновлена!", Toast.LENGTH_SHORT).show();
            }
            finish();
        });
    }
}
