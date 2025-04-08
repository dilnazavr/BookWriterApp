package com.example.bookwriterapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.VideoView;
import android.media.MediaPlayer;
import android.net.Uri;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    private VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        videoView = findViewById(R.id.videoView);

        // Путь к видеофайлу в папке res/raw
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.splash_video);
        videoView.setVideoURI(uri);

        // Запуск видео
        videoView.setOnPreparedListener(mp -> videoView.start());

        // После завершения видео запускаем MainActivity
        videoView.setOnCompletionListener(mp -> {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // Закрыть SplashActivity
        });
    }
}
