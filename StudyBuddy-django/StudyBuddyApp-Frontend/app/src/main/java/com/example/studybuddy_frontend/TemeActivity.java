package com.example.studybuddy_frontend;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TemeActivity extends AppCompatActivity {

    private RecyclerView listaTeme;
    private ArrayList<String> teme;
    private TemaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_teme);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        listaTeme = findViewById(R.id.listaTeme);
        listaTeme.setLayoutManager(new LinearLayoutManager(this));

        teme = new ArrayList<>();

    }


    public void goBack(View view){
        finish();
    }

    public void addTema(View view){
        Toast.makeText(this, "Adauga o tema noua!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, AddTemaActivity.class);
        startActivity(intent);
    }
}