package com.example.studybuddy_frontend;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface APIService {
    @GET("teme") // Endpoint-ul API-ului pentru a obține temele
    Call<List<Tema>> getTeme(); // Returnează o listă de teme
}
