package com.example.studybuddy_frontend;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TemaAdapter extends RecyclerView.Adapter<TemaAdapter.TemaViewHolder> {
    private List<Tema> temeList;

    public TemaAdapter(List<Tema> temeList) {
        this.temeList = temeList;
    }

    @NonNull
    @Override
    public TemaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tema, parent, false);
        return new TemaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TemaViewHolder holder, int position) {
        Tema tema = temeList.get(position);
        holder.txtNume.setText(tema.getTitlu());
        holder.txtDescriere.setText(tema.getDescriere());
        holder.txtDeadline.setText(tema.getDeadline());
    }

    @Override
    public int getItemCount() {
        return temeList.size();
    }

    public static class TemaViewHolder extends RecyclerView.ViewHolder {
        TextView txtNume, txtDescriere, txtDeadline;

        public TemaViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNume = itemView.findViewById(R.id.txtNume);
            txtDescriere = itemView.findViewById(R.id.txtDescriere);
            txtDeadline = itemView.findViewById(R.id.txtDeadline);
        }
    }
}
