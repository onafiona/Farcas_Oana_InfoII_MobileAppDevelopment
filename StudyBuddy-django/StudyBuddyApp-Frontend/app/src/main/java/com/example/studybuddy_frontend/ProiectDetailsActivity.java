package com.example.studybuddy_frontend;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProiectDetailsActivity extends AppCompatActivity {

    private TextView titlu, descriere, deadline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_proiect_details);

        titlu = findViewById(R.id.titluProiect);
        descriere = findViewById(R.id.descriereProiect);
        deadline = findViewById(R.id.deadlineProiect);

        Intent intent = getIntent();
        titlu.setText(intent.getStringExtra("titlu"));
        descriere.setText(intent.getStringExtra("descriere"));
        deadline.setText(intent.getStringExtra("deadline"));

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void handInProiect(View view) {
        int proiectId = getIntent().getIntExtra("id", -1);

        if (proiectId == -1) {
            Toast.makeText(this, "Eroare: nu s-a găsit proiectul!", Toast.LENGTH_SHORT).show();
            return;
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService apiService = retrofit.create(APIService.class);

        Call<Void> call = apiService.deleteProiect(proiectId);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(ProiectDetailsActivity.this, "Proiectul a fost predat cu succes!", Toast.LENGTH_SHORT).show();
                    finish();  // Înapoi la lista de teme
                } else {
                    Toast.makeText(ProiectDetailsActivity.this, "Eroare la predare!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(ProiectDetailsActivity.this, "Eroare de conexiune: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void goBack(View view) {
        finish();  // Închide activitatea și revine la ecranul anterior
    }

}