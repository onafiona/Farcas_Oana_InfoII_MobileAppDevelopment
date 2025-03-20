package com.example.studybuddy_frontend;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface APIService {
    @GET("api/teme/") // Endpoint-ul API-ului pentru a obține temele
    Call<List<Tema>> getTeme(); // Returnează o listă de teme

    @GET("api/proiecte/")
    Call<List<Proiect>> getProiecte();

    @GET("api/examene/")
    Call<List<Examen>> getExamene();
}
