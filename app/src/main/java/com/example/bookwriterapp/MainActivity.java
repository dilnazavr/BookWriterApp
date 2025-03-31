package com.example.bookwriterapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.bookwriterapp.adapter.BookAdapter;
import com.example.bookwriterapp.database.DatabaseHelper;
import com.example.bookwriterapp.model.Book;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rvBooks;
    private BookAdapter bookAdapter;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvBooks = findViewById(R.id.rvBooks);
        Button btnAddBook = findViewById(R.id.btnAddBook);

        db = new DatabaseHelper(this);
        loadBooks();

        btnAddBook.setOnClickListener(v -> {
            db.addBook("Новая книга");
            loadBooks();
        });
    }

    private void loadBooks() {
        List<Book> books = db.getAllBooks();
        bookAdapter = new BookAdapter(books, book -> {
            Intent intent = new Intent(MainActivity.this, ChapterListActivity.class);
            intent.putExtra("BOOK_ID", book.getId());
            startActivity(intent);
        });

        rvBooks.setLayoutManager(new LinearLayoutManager(this));
        rvBooks.setAdapter(bookAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadBooks();
    }
}
