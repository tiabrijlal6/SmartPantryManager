package com.example.smartpantrymanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class SuggestedRecipesActivity extends AppCompatActivity {

    private RecyclerView recyclerViewSuggestedRecipes;
    private TextView textNoRecipes;
    private DatabaseHelper databaseHelper;
    private RecipeAdapter recipeAdapter;
    private ArrayList<Recipe> suggestedRecipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_suggested_recipes);

        recyclerViewSuggestedRecipes = findViewById(R.id.recyclerViewSuggestedRecipes);
        textNoRecipes = findViewById(R.id.textNoRecipes);
        BottomNavigationView bottomNavigation = findViewById(R.id.bottomNavigation);

        databaseHelper = new DatabaseHelper(this);

        recyclerViewSuggestedRecipes.setLayoutManager(
                new LinearLayoutManager(this)
        );

        bottomNavigation.setSelectedItemId(R.id.navRecipes);

        bottomNavigation.setOnItemSelectedListener(item -> {

            int itemId = item.getItemId();

            if (itemId == R.id.navRecipes) {
                return true;

            } else if (itemId == R.id.navPantry) {

                Intent intent = new Intent(
                        SuggestedRecipesActivity.this,
                        MainActivity.class
                );
                startActivity(intent);
                return true;

            } else if (itemId == R.id.navSettings) {

                Intent intent = new Intent(
                        SuggestedRecipesActivity.this,
                        SettingsActivity.class
                );
                startActivity(intent);
                return true;
            }

            return false;
        });

        ViewCompat.setOnApplyWindowInsetsListener(
                findViewById(R.id.main),
                (v, insets) -> {

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
                }
        );
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadSuggestedRecipes();
    }

    private void loadSuggestedRecipes() {

        ArrayList<PantryItem> pantryItems = databaseHelper.getAllPantryItems();
        ArrayList<Recipe> allRecipes = databaseHelper.getAllRecipes();

        suggestedRecipes = new ArrayList<>();

        for (Recipe recipe : allRecipes) {

            ArrayList<RecipeIngredient> requiredIngredients =
                    databaseHelper.getRecipeIngredients(recipe.getId());

            if (IngredientMatcher.canMakeRecipe(
                    pantryItems,
                    requiredIngredients
            )) {
                suggestedRecipes.add(recipe);
            }
        }

        recipeAdapter = new RecipeAdapter(suggestedRecipes);
        recyclerViewSuggestedRecipes.setAdapter(recipeAdapter);

        if (suggestedRecipes.isEmpty()) {
            textNoRecipes.setVisibility(View.VISIBLE);
            recyclerViewSuggestedRecipes.setVisibility(View.GONE);
        } else {
            textNoRecipes.setVisibility(View.GONE);
            recyclerViewSuggestedRecipes.setVisibility(View.VISIBLE);
        }
    }
}