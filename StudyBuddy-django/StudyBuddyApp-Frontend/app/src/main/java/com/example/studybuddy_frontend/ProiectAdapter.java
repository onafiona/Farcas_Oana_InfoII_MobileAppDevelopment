package com.example.studybuddy_frontend;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProiectAdapter extends RecyclerView.Adapter<ProiectAdapter.ProiectViewHolder> {
    private List<Proiect> proiecteList;

    public ProiectAdapter(List<Proiect> proiecteList) {
        this.proiecteList = proiecteList;
    }

    @NonNull
    @Override
    public ProiectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_proiect, parent, false);
        return new ProiectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProiectViewHolder holder, int position) {
        Proiect proiect = proiecteList.get(position);
        holder.txtTitlu.setText(proiect.getTitlu());
        holder.txtDescriere.setText(proiect.getDescriere());
        holder.txtDeadline.setText(proiect.getDeadline());
    }

    @Override
    public int getItemCount() {
        return proiecteList.size();
    }

    public static class ProiectViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitlu, txtDescriere, txtDeadline;

        public ProiectViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitlu = itemView.findViewById(R.id.txtTitlu);
            txtDescriere = itemView.findViewById(R.id.txtDescriere);
            txtDeadline = itemView.findViewById(R.id.txtDeadline);
        }
    }
}
