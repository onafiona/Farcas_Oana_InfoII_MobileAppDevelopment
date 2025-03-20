package com.example.studybuddy_frontend;

public class Examen {
    private String materie_nume;
    private String descriere;
    private String deadline;

    // Constructor
    public Examen(String materie_nume, String descriere, String deadline) {
        this.materie_nume = materie_nume;
        this.descriere = descriere;
        this.deadline = deadline;
    }

    // Getteri
    public String getMaterie_nume() {
        return materie_nume;
    }

    public String getDescriere() {
        return descriere;
    }

    public String getDeadline() {
        return deadline;
    }
}
