package com.example.smartpantrymanager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import android.widget.Toast;
import android.content.Intent;

public class PantryAdapter extends RecyclerView.Adapter<PantryAdapter.PantryViewHolder> {

    private ArrayList<PantryItem> pantryItems;

    public PantryAdapter(ArrayList<PantryItem> pantryItems) {
        this.pantryItems = pantryItems;
    }

    public static class PantryViewHolder extends RecyclerView.ViewHolder {

        TextView textIngredientName;
        TextView textIngredientQuantity;
        TextView textIngredientExpiry;
        Button buttonEditIngredient;
        Button buttonDeleteIngredient;

        public PantryViewHolder(@NonNull View itemView) {
            super(itemView);

            textIngredientName = itemView.findViewById(R.id.textIngredientName);
            textIngredientQuantity = itemView.findViewById(R.id.textIngredientQuantity);
            textIngredientExpiry = itemView.findViewById(R.id.textIngredientExpiry);

            buttonEditIngredient = itemView.findViewById(R.id.buttonEditIngredient);
            buttonDeleteIngredient = itemView.findViewById(R.id.buttonDeleteIngredient);
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

        holder.buttonDeleteIngredient.setOnClickListener(v -> {

            DatabaseHelper databaseHelper = new DatabaseHelper(holder.itemView.getContext());

            boolean deleted = databaseHelper.deletePantryItem(pantryItem.getId());

            if (deleted) {
                pantryItems.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, pantryItems.size());

                Toast.makeText(
                        holder.itemView.getContext(),
                        "Ingredient deleted",
                        Toast.LENGTH_SHORT
                ).show();
            } else {
                Toast.makeText(
                        holder.itemView.getContext(),
                        "Ingredient could not be deleted",
                        Toast.LENGTH_SHORT
                ).show();
            }
        });

        holder.buttonEditIngredient.setOnClickListener(v -> {

            Intent intent = new Intent(
                    holder.itemView.getContext(),
                    AddEditIngredientActivity.class
            );

            intent.putExtra("ITEM_ID", pantryItem.getId());
            intent.putExtra("ITEM_NAME", pantryItem.getName());
            intent.putExtra("ITEM_QUANTITY", pantryItem.getQuantity());
            intent.putExtra("ITEM_UNIT", pantryItem.getUnit());
            intent.putExtra("ITEM_EXPIRY", pantryItem.getExpiryDate());

            holder.itemView.getContext().startActivity(intent);
        });
    }
    @Override
    public int getItemCount() {
        return pantryItems.size();
    }
}
