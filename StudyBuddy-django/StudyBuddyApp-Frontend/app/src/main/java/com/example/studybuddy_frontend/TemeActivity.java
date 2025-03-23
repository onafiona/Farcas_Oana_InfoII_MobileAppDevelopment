package com.example.studybuddy_frontend;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TemeActivity extends AppCompatActivity {

    private RecyclerView listaTeme;
    private List<Tema> teme = new ArrayList<>();
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

        adapter = new TemaAdapter(teme, tema -> {
            Intent intent = new Intent(TemeActivity.this, TemaDetailsActivity.class);
            intent.putExtra("id", tema.getId());
            intent.putExtra("titlu", tema.getTitlu());
            intent.putExtra("descriere", tema.getDescriere());
            intent.putExtra("deadline", tema.getDeadline());
            startActivity(intent);
        });
        listaTeme.setAdapter(adapter);

        fetchTeme();

    }

    private void fetchTeme() {
        // 1. Creăm conexiunea la server
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000/") // adresa serverului local pentru emulator
                .addConverterFactory(GsonConverterFactory.create()) // transformă datele JSON în Java
                .build();

        // 2. Creăm serviciul API care știe să "vorbească" cu serverul
        APIService apiService = retrofit.create(APIService.class);

        // 3. Cerem temele de la server (GET)
        Call<List<Tema>> call = apiService.getTeme();

        // 4. Așteptăm răspunsul de la server
        call.enqueue(new Callback<List<Tema>>() {

            // Când primim răspuns OK (200)
            @Override
            public void onResponse(Call<List<Tema>> call, Response<List<Tema>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Am primit temele! 🥳
                    teme.clear();  // ștergem lista veche
                    teme.addAll(response.body());  // adăugăm noile teme
                    adapter.notifyDataSetChanged();  // actualizăm RecyclerView-ul
                } else {
                    // Dacă nu avem teme sau serverul nu răspunde corect
                    Log.e("API_RESPONSE", "Cod răspuns: " + response.code());
                    Log.e("API_RESPONSE", "Răspuns complet: " + response.errorBody());
                    Toast.makeText(TemeActivity.this, "Nu s-au găsit teme!", Toast.LENGTH_SHORT).show();
                }
            }

            // Când serverul NU răspunde sau avem eroare de rețea
            @Override
            public void onFailure(Call<List<Tema>> call, Throwable t) {
                Toast.makeText(TemeActivity.this, "Eroare la conectare: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                t.printStackTrace();  // Afișează eroarea detaliată în Logcat
                Log.e("API_ERROR", "Eroare: " + t.getMessage()); // Log-ul cu eroarea
            }
        });
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