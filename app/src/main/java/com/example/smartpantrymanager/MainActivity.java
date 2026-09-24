package com.example.smartpantrymanager;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Intent;
import android.widget.Button;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

        buttonAddIngredient.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddEditIngredientActivity.class);
            startActivity(intent);
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    @Override
    protected void onResume() {
        super.onResume();

        pantryItems = databaseHelper.getAllPantryItems();
        pantryAdapter = new PantryAdapter(pantryItems);
        recyclerViewPantry.setAdapter(pantryAdapter);
    }
}