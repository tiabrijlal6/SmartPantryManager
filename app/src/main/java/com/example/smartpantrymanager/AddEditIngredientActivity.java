package com.example.smartpantrymanager;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddEditIngredientActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_edit_ingredient);
        EditText editTextIngredientName = findViewById(R.id.editTextIngredientName);
        EditText editTextQuantity = findViewById(R.id.editTextQuantity);
        EditText editTextUnit = findViewById(R.id.editTextUnit);
        EditText editTextExpiryDate = findViewById(R.id.editTextExpiryDate);
        Button buttonSaveIngredient = findViewById(R.id.buttonSaveIngredient);

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        buttonSaveIngredient.setOnClickListener(v -> {

            String name = editTextIngredientName.getText().toString().trim();
            String quantityText = editTextQuantity.getText().toString().trim();
            String unit = editTextUnit.getText().toString().trim();
            String expiryDate = editTextExpiryDate.getText().toString().trim();

            if (name.isEmpty()) {
                editTextIngredientName.setError("Ingredient name is required");
                return;
            }

            if (quantityText.isEmpty()) {
                editTextQuantity.setError("Quantity is required");
                return;
            }

            if (unit.isEmpty()) {
                editTextUnit.setError("Unit is required");
                return;
            }

            double quantity;

            try {
                quantity = Double.parseDouble(quantityText);
            } catch (NumberFormatException e) {
                editTextQuantity.setError("Enter a valid quantity");
                return;
            }

            if (quantity <= 0) {
                editTextQuantity.setError("Quantity must be greater than 0");
                return;
            }

            boolean isInserted = databaseHelper.addPantryItem(
                    name,
                    quantity,
                    unit,
                    expiryDate
            );

            if (isInserted) {
                Toast.makeText(this, "Ingredient saved", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Ingredient could not be saved", Toast.LENGTH_SHORT).show();
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}