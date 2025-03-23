package com.example.studybuddy_frontend;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.app.DatePickerDialog;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;


public class AddTemaActivity extends AppCompatActivity {

    private EditText Titlu, Descriere;
    private TextView Deadline;
    private Spinner spinnerMaterie;
    private String selectedDate="";
    private ArrayList<String> materiiList = new ArrayList<>();
    private ArrayList<Integer> materiiIdList = new ArrayList<>();
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_tema);
        Titlu=findViewById(R.id.etTitlu);
        Descriere=findViewById(R.id.etDescriere);
        Deadline=findViewById(R.id.tvDeadline);
        spinnerMaterie = findViewById(R.id.spinnerMaterie);

        queue = Volley.newRequestQueue(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        queue = Volley.newRequestQueue(this); //??????
        loadMaterii();

    }

    private void loadMaterii() {
        String url = "http://10.0.2.2:8000/api/materii/";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject materie = response.getJSONObject(i);
                            String numeMaterie = materie.getString("nume");
                            int idMaterie = materie.getInt("id");

                            materiiList.add(numeMaterie);
                            materiiIdList.add(idMaterie);
                        }

                        // Setăm adapter pentru spinner
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                                android.R.layout.simple_spinner_dropdown_item, materiiList);
                        spinnerMaterie.setAdapter(adapter);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Eroare la încărcarea materiilor!", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    Toast.makeText(this, "Nu s-au putut încărca materiile!", Toast.LENGTH_SHORT).show();
                    error.printStackTrace();
                });

        queue.add(request);
    }

    public void selecteazaData(View view){
        final Calendar calendar=Calendar.getInstance();
        int year= calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH);
        int day=calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view1, year1, month1, dayOfMonth) -> {
                    // Formatăm data în YYYY-MM-DD pentru backend
                    selectedDate = year1 + "-" + String.format("%02d", month1 + 1) + "-" + String.format("%02d", dayOfMonth);
                    Deadline.setText(selectedDate);
                },
                year, month, day);
        datePickerDialog.show();
    }

    public void salveazaTema(View view) {
        String titlu = Titlu.getText().toString();
        String descriere = Descriere.getText().toString();
        int selectedPosition = spinnerMaterie.getSelectedItemPosition();

        if (titlu.isEmpty() || descriere.isEmpty() || selectedDate.isEmpty() || selectedPosition == -1) {
            Toast.makeText(this, "Completează toate câmpurile!", Toast.LENGTH_SHORT).show();
            return;
        }

        int selectedMaterieId = materiiIdList.get(selectedPosition);

        // Construim JSON-ul
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("titlu", titlu);
            jsonObject.put("descriere", descriere);
            jsonObject.put("deadline", selectedDate);
            jsonObject.put("materie", selectedMaterieId);
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }

        // Trimitem datele către backend
        String url = "http://10.0.2.2:8000/api/teme/";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                response -> {
                    Toast.makeText(this, "Tema adăugată cu succes!", Toast.LENGTH_SHORT).show();
                    finish();
                },
                error -> {
                    Toast.makeText(this, "Eroare la salvare!", Toast.LENGTH_SHORT).show();
                    error.printStackTrace();
                });

        queue.add(request);
    }
    public void goBack(View view) {
        finish();  // Închide activitatea și revine la ecranul anterior
    }
}