package com.example.simpledictionary;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class AddEditActivity extends AppCompatActivity {

    EditText etWord, etMeaning;
    Button btnSave;

    int position = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);

        etWord = findViewById(R.id.etWord);
        etMeaning = findViewById(R.id.etMeaning);
        btnSave = findViewById(R.id.btnSave);

        // استقبال بيانات التعديل
        Intent intent = getIntent();
        if (intent.hasExtra("word")) {
            etWord.setText(intent.getStringExtra("word"));
            etMeaning.setText(intent.getStringExtra("meaning"));
            position = intent.getIntExtra("position", -1);
        }

        btnSave.setOnClickListener(v -> {

            String word = etWord.getText().toString();
            String meaning = etMeaning.getText().toString();

            Intent resultIntent = new Intent();
            resultIntent.putExtra("word", word);
            resultIntent.putExtra("meaning", meaning);
            resultIntent.putExtra("position", position);

            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }
}