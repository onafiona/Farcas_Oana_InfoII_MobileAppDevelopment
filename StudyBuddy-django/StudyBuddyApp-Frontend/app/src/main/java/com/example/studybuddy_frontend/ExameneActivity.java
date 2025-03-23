package com.example.studybuddy_frontend;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

public class ExameneActivity extends AppCompatActivity {

    private RecyclerView listaExamene;
    private List<Examen> examene=new ArrayList<>();
    private ExamenAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_examene);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        listaExamene=findViewById(R.id.listaExamene);
        listaExamene.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ExamenAdapter(examene, examen -> {
            Intent intent = new Intent(this, ExamenDetailsActivity.class);
            intent.putExtra("id", examen.getId());
            intent.putExtra("materie", examen.getMaterie_nume());
            intent.putExtra("descriere", examen.getDescriere());
            intent.putExtra("data", examen.getData_examen());
            startActivity(intent);
        });

        listaExamene.setAdapter(adapter);
        fetchExamene();
    }

    private void fetchExamene(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService apiService=retrofit.create(APIService.class);
        Call<List<Examen>> call=apiService.getExamene();
        call.enqueue(new Callback<List<Examen>>() {
            @Override
            public void onResponse(Call<List<Examen>> call, Response<List<Examen>> response) {
                if(response.isSuccessful() && response.body()!=null){
                    examene.clear();
                    examene.addAll(response.body());
                    adapter.notifyDataSetChanged();
                }else{
                    Toast.makeText(ExameneActivity.this, "Nu s-au găsit examene!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Examen>> call, Throwable t) {
                Toast.makeText(ExameneActivity.this, "Eroare la conectare: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void goBack(View view){
        finish();
    }

    public void addExamen(View view){
        Intent intent = new Intent(this, AddExamenActivity.class);
        startActivity(intent);
    }
}