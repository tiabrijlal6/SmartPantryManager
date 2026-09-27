package com.example.smartpantrymanager;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private ArrayList<Recipe> recipes;

    public RecipeAdapter(ArrayList<Recipe> recipes) {
        this.recipes = recipes;
    }

    public static class RecipeViewHolder extends RecyclerView.ViewHolder {

        TextView textRecipeName;
        TextView textRecipeStatus;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);

            textRecipeName = itemView.findViewById(R.id.textRecipeName);
            textRecipeStatus = itemView.findViewById(R.id.textRecipeStatus);
        }
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recipe, parent, false);

        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {

        Recipe recipe = recipes.get(position);

        holder.textRecipeName.setText(recipe.getName());
        holder.textRecipeStatus.setText("Ready to make");

        holder.itemView.setOnClickListener(v -> {

            Intent intent = new Intent(
                    holder.itemView.getContext(),
                    RecipeDetailActivity.class
            );

            intent.putExtra("RECIPE_ID", recipe.getId());
            intent.putExtra("RECIPE_NAME", recipe.getName());
            intent.putExtra("RECIPE_STEPS", recipe.getSteps());

            holder.itemView.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }
}