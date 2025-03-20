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

public class ProiecteActivity extends AppCompatActivity {

    private RecyclerView listaProiecte;
    private List<Proiect> proiecte=new ArrayList<>();
    private ProiectAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_proiecte);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        listaProiecte=findViewById(R.id.listaProiecte);
        listaProiecte.setLayoutManager(new LinearLayoutManager(this));
        adapter=new ProiectAdapter(proiecte);
        listaProiecte.setAdapter(adapter);
        fetchProiecte();
    }

    private void fetchProiecte(){
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService apiService=retrofit.create(APIService.class);
        Call<List<Proiect>> call=apiService.getProiecte();
        call.enqueue(new Callback<List<Proiect>>() {
            @Override
            public void onResponse(Call<List<Proiect>> call, Response<List<Proiect>> response) {
                Log.d("ProiecteActivity", "Răspuns primit, cod: " + response.code());
                if(response.isSuccessful() && response.body()!= null){
                    proiecte.clear();
                    proiecte.addAll(response.body());
                    adapter.notifyDataSetChanged();
                    Log.d("ProiecteActivity", "Proiecte încărcate: " + proiecte.size());
                }else{
                    Toast.makeText(ProiecteActivity.this, "Nu s-au găsit proiecte!", Toast.LENGTH_SHORT).show();
                    Log.d("ProiecteActivity", "Lista de proiecte e goală sau răspunsul nu e ok.");

                }
            }

            @Override
            public void onFailure(Call<List<Proiect>> call, Throwable t) {
                Toast.makeText(ProiecteActivity.this, "Eroare la conectare: "+ t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("ProiecteActivity", "Eșec la fetch: " + t.getMessage());
            }
        });
    }

    public void goBack(View view){
        finish();
    }

    public void addProiect(View view){
        Toast.makeText(this, "Adauga un proiect nou!", Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(this, AddProiectActivity.class);
        startActivity(intent);
    }
}