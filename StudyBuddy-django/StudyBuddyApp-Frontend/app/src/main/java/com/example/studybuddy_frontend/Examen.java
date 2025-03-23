package com.example.studybuddy_frontend;

public class Examen {
    private int id;
    private String materie_nume;
    private String descriere;
    private String data_examen;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public Examen(String materie_nume, String descriere, String data_examen) {
        this.materie_nume = materie_nume;
        this.descriere = descriere;
        this.data_examen = data_examen;
    }

    public String getMaterie_nume() {
        return materie_nume;
    }

    public String getDescriere() {
        return descriere;
    }

    public String getData_examen() {
        return data_examen;
    }
}
