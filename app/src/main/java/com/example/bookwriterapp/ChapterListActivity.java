package com.example.bookwriterapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.bookwriterapp.adapter.ChapterAdapter;
import com.example.bookwriterapp.database.DatabaseHelper;
import com.example.bookwriterapp.model.Chapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;

public class ChapterListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ChapterAdapter chapterAdapter;
    private List<Chapter> chapterList;
    private DatabaseHelper db;
    private int bookId;
    private TextView tvNoChapters;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_list);

        recyclerView = findViewById(R.id.recyclerViewChapters);
        tvNoChapters = findViewById(R.id.tvNoChapters);
        FloatingActionButton fabAddChapter = findViewById(R.id.fabAddChapter);

        db = new DatabaseHelper(this);
        bookId = getIntent().getIntExtra("BOOK_ID", -1);

        loadChapters();

        fabAddChapter.setOnClickListener(v -> {
            Intent intent = new Intent(ChapterListActivity.this, ChapterEditorActivity.class);
            intent.putExtra("BOOK_ID", bookId);
            startActivity(intent);
        });
    }

    private void loadChapters() {
        chapterList = db.getChaptersByBookId(bookId);
        if (chapterList.isEmpty()) {
            tvNoChapters.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            tvNoChapters.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            chapterAdapter = new ChapterAdapter(chapterList, chapter -> {
                Intent intent = new Intent(ChapterListActivity.this, ChapterEditorActivity.class);
                intent.putExtra("CHAPTER_ID", chapter.getID());
                startActivity(intent);
            });
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(chapterAdapter);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadChapters();
    }
}
