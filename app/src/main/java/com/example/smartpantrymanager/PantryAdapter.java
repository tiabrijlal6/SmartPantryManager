package com.example.smartpantrymanager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
public class PantryAdapter extends RecyclerView.Adapter<PantryAdapter.PantryViewHolder> {

    private ArrayList<PantryItem> pantryItems;

    public PantryAdapter(ArrayList<PantryItem> pantryItems) {
        this.pantryItems = pantryItems;
    }

    public static class PantryViewHolder extends RecyclerView.ViewHolder {

        TextView textIngredientName;
        TextView textIngredientQuantity;
        TextView textIngredientExpiry;

        public PantryViewHolder(@NonNull View itemView) {
            super(itemView);

            textIngredientName = itemView.findViewById(R.id.textIngredientName);
            textIngredientQuantity = itemView.findViewById(R.id.textIngredientQuantity);
            textIngredientExpiry = itemView.findViewById(R.id.textIngredientExpiry);
        }
    }
    @NonNull
    @Override
    public PantryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pantry, parent, false);

        return new PantryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PantryViewHolder holder, int position) {

        PantryItem pantryItem = pantryItems.get(position);

        holder.textIngredientName.setText(pantryItem.getName());

        String quantityText = pantryItem.getQuantity() + " " + pantryItem.getUnit();
        holder.textIngredientQuantity.setText(quantityText);

        String expiryDate = pantryItem.getExpiryDate();

        if (expiryDate == null || expiryDate.isEmpty()) {
            holder.textIngredientExpiry.setText("Expiry: No expiry date");
        } else {
            holder.textIngredientExpiry.setText("Expiry: " + expiryDate);
        }
    }
    @Override
    public int getItemCount() {
        return pantryItems.size();
    }
}
