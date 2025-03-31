package com.example.bookwriterapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bookwriterapp.R;
import com.example.bookwriterapp.database.UserDatabaseHelper;

public class LoginActivity extends AppCompatActivity {
    private EditText editEmail, editPassword;
    private UserDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbHelper = new UserDatabaseHelper(this);
        editEmail = findViewById(R.id.editTextEmail);
        editPassword = findViewById(R.id.editTextPassword);

        // Проверяем, был ли пользователь авторизован ранее
        checkIfAlreadyLoggedIn();

        findViewById(R.id.buttonLogin).setOnClickListener(v -> loginUser());
        findViewById(R.id.textRegister).setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterActivity.class));
            finish();  // Закрываем экран входа, чтобы пользователь не вернулся к нему
        });
    }

    private void loginUser() {
        String email = editEmail.getText().toString().trim();
        String password = editPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Введите email и пароль", Toast.LENGTH_SHORT).show();
            return;
        }

        if (dbHelper.checkUser(email, password)) {
            // Сохраняем информацию о том, что пользователь авторизован
            SharedPreferences sharedPreferences = getSharedPreferences("MyApp", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("isLoggedIn", true);
            editor.putString("userEmail", email);  // Можно также сохранить другие данные пользователя, если нужно
            editor.apply();

            Toast.makeText(this, "Добро пожаловать!", Toast.LENGTH_SHORT).show();

            // Переход на главный экран
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish(); // Закрываем экран входа, чтобы не вернуться обратно
        } else {
            Toast.makeText(this, "Неверные данные", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkIfAlreadyLoggedIn() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyApp", MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);

        if (isLoggedIn) {
            // Если пользователь уже вошел, перенаправляем его на главный экран
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();  // Закрываем экран входа
        }
    }
}
