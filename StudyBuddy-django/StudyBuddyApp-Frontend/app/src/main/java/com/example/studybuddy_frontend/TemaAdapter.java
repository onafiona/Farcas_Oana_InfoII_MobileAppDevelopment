package com.example.studybuddy_frontend;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TemaAdapter extends RecyclerView.Adapter<TemaAdapter.TemaViewHolder> {
    private List<String> temeList;

    public TemaAdapter(List<String> temeList) {
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
        String tema = temeList.get(position);
        holder.txtTema.setText(tema);
    }

    @Override
    public int getItemCount() {
        return temeList.size();
    }

    public static class TemaViewHolder extends RecyclerView.ViewHolder {
        TextView txtTema;

        public TemaViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTema = itemView.findViewById(R.id.txtTema);
        }
    }
}
