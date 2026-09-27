package com.example.smartpantrymanager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewPantry;
    private PantryAdapter pantryAdapter;
    private DatabaseHelper databaseHelper;
    private ArrayList<PantryItem> pantryItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        recyclerViewPantry = findViewById(R.id.recyclerViewPantry);
        databaseHelper = new DatabaseHelper(this);

        recyclerViewPantry.setLayoutManager(new LinearLayoutManager(this));

        pantryItems = databaseHelper.getAllPantryItems();
        pantryAdapter = new PantryAdapter(pantryItems);
        recyclerViewPantry.setAdapter(pantryAdapter);

        Button buttonAddIngredient = findViewById(R.id.buttonAddIngredient);
        BottomNavigationView bottomNavigation = findViewById(R.id.bottomNavigation);

        buttonAddIngredient.setOnClickListener(v -> {
            Intent intent = new Intent(
                    MainActivity.this,
                    AddEditIngredientActivity.class
            );
            startActivity(intent);
        });

        bottomNavigation.setSelectedItemId(R.id.navPantry);

        bottomNavigation.setOnItemSelectedListener(item -> {

            int itemId = item.getItemId();

            if (itemId == R.id.navPantry) {
                return true;

            } else if (itemId == R.id.navRecipes) {

                Intent intent = new Intent(
                        MainActivity.this,
                        SuggestedRecipesActivity.class
                );
                startActivity(intent);
                return true;

            } else if (itemId == R.id.navSettings) {

                Intent intent = new Intent(
                        MainActivity.this,
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

        pantryItems = databaseHelper.getAllPantryItems();
        pantryAdapter = new PantryAdapter(pantryItems);
        recyclerViewPantry.setAdapter(pantryAdapter);
    }
}