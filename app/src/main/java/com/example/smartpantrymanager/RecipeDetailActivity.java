package com.example.smartpantrymanager;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class RecipeDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_recipe_detail);

        TextView textRecipeDetailName = findViewById(R.id.textRecipeDetailName);
        TextView textRecipeIngredients = findViewById(R.id.textRecipeIngredients);
        TextView textRecipeSteps = findViewById(R.id.textRecipeSteps);

        int recipeId = getIntent().getIntExtra("RECIPE_ID", -1);
        String recipeName = getIntent().getStringExtra("RECIPE_NAME");
        String recipeSteps = getIntent().getStringExtra("RECIPE_STEPS");

        textRecipeDetailName.setText(recipeName);
        textRecipeSteps.setText(recipeSteps);

        DatabaseHelper databaseHelper = new DatabaseHelper(this);

        ArrayList<RecipeIngredient> ingredients =
                databaseHelper.getRecipeIngredients(recipeId);

        StringBuilder ingredientText = new StringBuilder();

        for (RecipeIngredient ingredient : ingredients) {
            ingredientText
                    .append("• ")
                    .append(ingredient.getQuantity())
                    .append(" ")
                    .append(ingredient.getUnit())
                    .append(" ")
                    .append(ingredient.getIngredientName())
                    .append("\n");
        }

        textRecipeIngredients.setText(ingredientText.toString());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {

            Insets systemBars = insets.getInsets(
                    WindowInsetsCompat.Type.systemBars()
            );

            v.setPadding(
                    systemBars.left,
                    systemBars.top,
                    systemBars.right,
                    systemBars.bottom
            );

            return insets;
        });
    }
}