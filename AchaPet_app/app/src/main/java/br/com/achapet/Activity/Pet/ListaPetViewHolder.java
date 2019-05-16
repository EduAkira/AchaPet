package br.com.achapet.Activity.Pet;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import br.com.achapet.R;

public class ListaPetViewHolder extends RecyclerView.ViewHolder {

    private TextView descriptionTextView;
    private TextView nameTextView;
    private TextView dateTextView;

    public ListaPetViewHolder(@NonNull View itemView) {
        super(itemView);
        descriptionTextView = itemView.findViewById(R.id.titulo1);
        nameTextView = itemView.findViewById(R.id.titulo2);
        dateTextView = itemView.findViewById(R.id.titulo3);
    }

    public TextView getDescriptionTextView() {
        return descriptionTextView;
    }

    public TextView getNameTextView() {
        return nameTextView;
    }

    public TextView getDateTextView() {
        return dateTextView;
    }
}
