package com.example.smartpantrymanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Switch;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SettingsActivity extends AppCompatActivity {

    private Switch switchExpiryAlerts;
    private Spinner spinnerUnitPreference;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_settings);

        switchExpiryAlerts = findViewById(R.id.switchExpiryAlerts);
        spinnerUnitPreference = findViewById(R.id.spinnerUnitPreference);
        BottomNavigationView bottomNavigation = findViewById(R.id.bottomNavigation);

        sharedPreferences = getSharedPreferences(
                "SmartPantrySettings",
                MODE_PRIVATE
        );

        String[] unitOptions = {
                "Metric (g, kg, ml, L)",
                "Standard item units"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                unitOptions
        );

        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        spinnerUnitPreference.setAdapter(adapter);

        boolean expiryAlertsEnabled = sharedPreferences.getBoolean(
                "EXPIRY_ALERTS",
                true
        );

        int selectedUnit = sharedPreferences.getInt(
                "UNIT_PREFERENCE",
                0
        );

        switchExpiryAlerts.setChecked(expiryAlertsEnabled);
        spinnerUnitPreference.setSelection(selectedUnit);

        switchExpiryAlerts.setOnCheckedChangeListener((buttonView, isChecked) -> {
            sharedPreferences.edit()
                    .putBoolean("EXPIRY_ALERTS", isChecked)
                    .apply();
        });

        spinnerUnitPreference.setOnItemSelectedListener(
                new android.widget.AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(
                            android.widget.AdapterView<?> parent,
                            android.view.View view,
                            int position,
                            long id) {

                        sharedPreferences.edit()
                                .putInt("UNIT_PREFERENCE", position)
                                .apply();
                    }

                    @Override
                    public void onNothingSelected(
                            android.widget.AdapterView<?> parent) {
                    }
                }
        );

        bottomNavigation.setSelectedItemId(R.id.navSettings);

        bottomNavigation.setOnItemSelectedListener(item -> {

            int itemId = item.getItemId();

            if (itemId == R.id.navSettings) {
                return true;

            } else if (itemId == R.id.navPantry) {

                Intent intent = new Intent(
                        SettingsActivity.this,
                        MainActivity.class
                );
                startActivity(intent);
                return true;

            } else if (itemId == R.id.navRecipes) {

                Intent intent = new Intent(
                        SettingsActivity.this,
                        SuggestedRecipesActivity.class
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
}