package com.example.studybuddy_frontend;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ExamenAdapter extends RecyclerView.Adapter<ExamenAdapter.ExamenViewHolder> {
    private List<Examen> exameneList;

    public ExamenAdapter(List<Examen> exameneList) {
        this.exameneList = exameneList;
    }

    @NonNull
    @Override
    public ExamenViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_examen, parent, false);
        return new ExamenViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExamenViewHolder holder, int position) {
        Examen examen = exameneList.get(position);
        holder.txtMaterie.setText(examen.getMaterie_nume());
        holder.txtDescriere.setText(examen.getDescriere());
        holder.txtDeadline.setText(examen.getDeadline());
    }

    @Override
    public int getItemCount() {
        return exameneList.size();
    }

    public static class ExamenViewHolder extends RecyclerView.ViewHolder {
        TextView txtMaterie, txtDescriere, txtDeadline;

        public ExamenViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMaterie = itemView.findViewById(R.id.txtMaterie);
            txtDescriere = itemView.findViewById(R.id.txtDescriere);
            txtDeadline = itemView.findViewById(R.id.txtDeadline);
        }
    }
}
