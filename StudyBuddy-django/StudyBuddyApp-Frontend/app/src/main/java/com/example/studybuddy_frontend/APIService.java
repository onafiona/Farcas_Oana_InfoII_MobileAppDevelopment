package com.example.studybuddy_frontend;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface APIService {
    @GET("api/teme/")
    Call<List<Tema>> getTeme();

    @GET("api/proiecte/")
    Call<List<Proiect>> getProiecte();

    @GET("api/examene/")
    Call<List<Examen>> getExamene();

    @DELETE("api/teme/{id}/")
    Call<Void> deleteTema(@Path("id") int id);

    @DELETE("api/proiecte/{id}/")
    Call<Void> deleteProiect(@Path("id") int id);

    @DELETE("api/examene/{id}/")
    Call<Void> deleteExamen(@Path("id") int id);


}
