package com.example.simpledictionary;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simpledictionary.AddEditActivity;
import com.example.simpledictionary.R;
import com.example.simpledictionary.WordAdapter;
import com.example.simpledictionary.WordItem;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton fab;

    ArrayList<WordItem> wordList;
    WordAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        fab = findViewById(R.id.fabAdd);

        // 1. البيانات
        if (savedInstanceState != null) {

            wordList = (ArrayList<WordItem>)
                    savedInstanceState.getSerializable("words");

        } else {

            wordList = new ArrayList<>();

            // بيانات تجريبية
            wordList.add(new WordItem("Hello", "مرحبا"));
            wordList.add(new WordItem("Book", "كتاب"));
            wordList.add(new WordItem("Car", "سيارة"));
        }

        // 2. Adapter
        adapter = new WordAdapter(wordList, position -> {
            // عند الضغط = تعديل (مؤقتاً نعرض Toast)
            Intent intent = new Intent(MainActivity.this, AddEditActivity.class);

            intent.putExtra("word", wordList.get(position).getWord());
            intent.putExtra("meaning", wordList.get(position).getMeaning());
            intent.putExtra("position", position);

            addWordLauncher.launch(intent);
        });

        // 3. RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // 4. FAB → إضافة كلمة جديدة
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddEditActivity.class);
            addWordLauncher.launch(intent);
        });

    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("words", wordList);
    }
    // استقبال البيانات من AddEditActivity
    ActivityResultLauncher<Intent> addWordLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        if (result.getResultCode() == RESULT_OK && result.getData() != null) {

                            String word = result.getData().getStringExtra("word");
                            String meaning = result.getData().getStringExtra("meaning");

                            int position = result.getData().getIntExtra("position", -1);

                            if (position == -1) {
                                // إضافة
                                wordList.add(new WordItem(word, meaning));
                                Toast.makeText(this, "Word Added", Toast.LENGTH_SHORT).show();
                            } else {
                                // تعديل
                                wordList.get(position).setWord(word);
                                wordList.get(position).setMeaning(meaning);
                                Toast.makeText(this, "Word Updated", Toast.LENGTH_SHORT).show();
                            }

                            adapter.notifyDataSetChanged();


                        }
                    }
            );
}