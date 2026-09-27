package com.example.smartpantrymanager;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;
import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "SmartPantry.db";
    private static final int DATABASE_VERSION = 2;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        String createPantryTable = "CREATE TABLE pantry_items (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL, " +
                "quantity REAL NOT NULL, " +
                "unit TEXT NOT NULL, " +
                "expiry_date TEXT)";

        db.execSQL(createPantryTable);

        String createRecipesTable = "CREATE TABLE recipes (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL, " +
                "steps TEXT NOT NULL)";

        String createRecipeIngredientsTable = "CREATE TABLE recipe_ingredients (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "recipe_id INTEGER NOT NULL, " +
                "ingredient_name TEXT NOT NULL, " +
                "quantity REAL NOT NULL, " +
                "unit TEXT NOT NULL)";

        db.execSQL(createRecipesTable);
        db.execSQL(createRecipeIngredientsTable);
        seedRecipes(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if (oldVersion < 2) {

            String createRecipesTable = "CREATE TABLE recipes (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "name TEXT NOT NULL, " +
                    "steps TEXT NOT NULL)";

            String createRecipeIngredientsTable = "CREATE TABLE recipe_ingredients (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "recipe_id INTEGER NOT NULL, " +
                    "ingredient_name TEXT NOT NULL, " +
                    "quantity REAL NOT NULL, " +
                    "unit TEXT NOT NULL)";

            db.execSQL(createRecipesTable);
            db.execSQL(createRecipeIngredientsTable);
            seedRecipes(db);
        }
    }

    public boolean addPantryItem(String name, double quantity, String unit, String expiryDate) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("quantity", quantity);
        values.put("unit", unit);
        values.put("expiry_date", expiryDate);

        long result = db.insert("pantry_items", null, values);

        return result != -1;
    }

    public ArrayList<PantryItem> getAllPantryItems() {

        ArrayList<PantryItem> pantryItems = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM pantry_items ORDER BY name ASC",
                null
        );

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                double quantity = cursor.getDouble(cursor.getColumnIndexOrThrow("quantity"));
                String unit = cursor.getString(cursor.getColumnIndexOrThrow("unit"));
                String expiryDate = cursor.getString(cursor.getColumnIndexOrThrow("expiry_date"));

                PantryItem pantryItem = new PantryItem(
                        id,
                        name,
                        quantity,
                        unit,
                        expiryDate
                );

                pantryItems.add(pantryItem);

            } while (cursor.moveToNext());
        }

        cursor.close();

        return pantryItems;
    }

    public boolean deletePantryItem(int id) {

        SQLiteDatabase db = this.getWritableDatabase();

        int result = db.delete(
                "pantry_items",
                "id = ?",
                new String[]{String.valueOf(id)}
        );

        return result > 0;
    }
    public boolean updatePantryItem(int id, String name, double quantity, String unit, String expiryDate) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("quantity", quantity);
        values.put("unit", unit);
        values.put("expiry_date", expiryDate);

        int result = db.update(
                "pantry_items",
                values,
                "id = ?",
                new String[]{String.valueOf(id)}
        );

        return result > 0;
    }
    private void seedRecipes(SQLiteDatabase db) {
        long scrambledEggsId = insertRecipe(
                db,
                "Scrambled Eggs",
                "Crack the eggs into a bowl and whisk. Heat the butter in a pan. Add the eggs and stir gently until cooked."
        );

        insertRecipeIngredient(db, scrambledEggsId, "egg", 2, "items");
        insertRecipeIngredient(db, scrambledEggsId, "butter", 10, "g");

        long cheeseOmeletteId = insertRecipe(
                db,
                "Cheese Omelette",
                "Beat the eggs in a bowl. Melt the butter in a pan. Add the eggs, sprinkle the cheese over them and cook until set."
        );

        insertRecipeIngredient(db, cheeseOmeletteId, "egg", 2, "items");
        insertRecipeIngredient(db, cheeseOmeletteId, "cheese", 50, "g");
        insertRecipeIngredient(db, cheeseOmeletteId, "butter", 10, "g");

        long friedEggId = insertRecipe(
                db,
                "Fried Egg",
                "Heat the butter in a pan. Crack the egg into the pan and cook until the white is set."
        );

        insertRecipeIngredient(db, friedEggId, "egg", 1, "items");
        insertRecipeIngredient(db, friedEggId, "butter", 5, "g");

        long cheeseToastId = insertRecipe(
                db,
                "Cheese Toast",
                "Place the cheese on the bread. Toast or grill until the bread is crisp and the cheese has melted."
        );

        insertRecipeIngredient(db, cheeseToastId, "bread", 2, "slices");
        insertRecipeIngredient(db, cheeseToastId, "cheese", 50, "g");

        long tomatoSandwichId = insertRecipe(
                db,
                "Tomato Sandwich",
                "Slice the tomato. Spread butter on the bread, add the tomato and close the sandwich."
        );

        insertRecipeIngredient(db, tomatoSandwichId, "bread", 2, "slices");
        insertRecipeIngredient(db, tomatoSandwichId, "tomato", 1, "items");
        insertRecipeIngredient(db, tomatoSandwichId, "butter", 10, "g");

        long eggSandwichId = insertRecipe(
                db,
                "Egg Sandwich",
                "Cook the egg in a pan. Spread butter on the bread and place the cooked egg between the slices."
        );

        insertRecipeIngredient(db, eggSandwichId, "bread", 2, "slices");
        insertRecipeIngredient(db, eggSandwichId, "egg", 1, "items");
        insertRecipeIngredient(db, eggSandwichId, "butter", 5, "g");

        long bananaToastId = insertRecipe(
                db,
                "Banana Toast",
                "Toast the bread. Slice the banana and place the slices on top of the toast."
        );

        insertRecipeIngredient(db, bananaToastId, "bread", 2, "slices");
        insertRecipeIngredient(db, bananaToastId, "banana", 1, "items");

        long butteredToastId = insertRecipe(
                db,
                "Buttered Toast",
                "Toast the bread until crisp and spread butter over the warm toast."
        );

        insertRecipeIngredient(db, butteredToastId, "bread", 2, "slices");
        insertRecipeIngredient(db, butteredToastId, "butter", 10, "g");

        long cheeseTomatoToastId = insertRecipe(
                db,
                "Cheese and Tomato Toast",
                "Place sliced tomato and cheese on the bread. Toast until the cheese has melted."
        );

        insertRecipeIngredient(db, cheeseTomatoToastId, "bread", 2, "slices");
        insertRecipeIngredient(db, cheeseTomatoToastId, "cheese", 40, "g");
        insertRecipeIngredient(db, cheeseTomatoToastId, "tomato", 1, "items");

        long oatmealId = insertRecipe(
                db,
                "Oatmeal",
                "Add the oats and milk to a pot. Cook over medium heat while stirring until thick and creamy."
        );

        insertRecipeIngredient(db, oatmealId, "oats", 50, "g");
        insertRecipeIngredient(db, oatmealId, "milk", 250, "ml");

        long bananaOatmealId = insertRecipe(
                db,
                "Banana Oatmeal",
                "Cook the oats with milk until soft. Slice the banana and add it on top."
        );

        insertRecipeIngredient(db, bananaOatmealId, "oats", 50, "g");
        insertRecipeIngredient(db, bananaOatmealId, "milk", 250, "ml");
        insertRecipeIngredient(db, bananaOatmealId, "banana", 1, "items");

        long yogurtBananaBowlId = insertRecipe(
                db,
                "Yogurt Banana Bowl",
                "Place the yogurt in a bowl. Slice the banana and add it on top."
        );

        insertRecipeIngredient(db, yogurtBananaBowlId, "yogurt", 150, "g");
        insertRecipeIngredient(db, yogurtBananaBowlId, "banana", 1, "items");

        long tomatoEggScrambleId = insertRecipe(
                db,
                "Tomato Egg Scramble",
                "Chop the tomato. Melt the butter in a pan, add the tomato and then add the beaten eggs. Cook while stirring."
        );

        insertRecipeIngredient(db, tomatoEggScrambleId, "egg", 2, "items");
        insertRecipeIngredient(db, tomatoEggScrambleId, "tomato", 1, "items");
        insertRecipeIngredient(db, tomatoEggScrambleId, "butter", 10, "g");

        long cheeseScrambledEggsId = insertRecipe(
                db,
                "Cheese Scrambled Eggs",
                "Beat the eggs. Melt the butter in a pan, add the eggs and stir. Add the cheese and continue cooking until melted."
        );

        insertRecipeIngredient(db, cheeseScrambledEggsId, "egg", 2, "items");
        insertRecipeIngredient(db, cheeseScrambledEggsId, "cheese", 40, "g");
        insertRecipeIngredient(db, cheeseScrambledEggsId, "butter", 10, "g");

        long frenchToastId = insertRecipe(
                db,
                "French Toast",
                "Beat the egg and milk together. Dip the bread into the mixture. Melt the butter in a pan and cook the bread on both sides."
        );

        insertRecipeIngredient(db, frenchToastId, "bread", 2, "slices");
        insertRecipeIngredient(db, frenchToastId, "egg", 1, "items");
        insertRecipeIngredient(db, frenchToastId, "milk", 50, "ml");
        insertRecipeIngredient(db, frenchToastId, "butter", 10, "g");

        long pancakesId = insertRecipe(
                db,
                "Pancakes",
                "Mix the flour, milk, egg and sugar together. Melt the butter in a pan and cook small portions of batter until golden on both sides."
        );

        insertRecipeIngredient(db, pancakesId, "flour", 100, "g");
        insertRecipeIngredient(db, pancakesId, "milk", 150, "ml");
        insertRecipeIngredient(db, pancakesId, "egg", 1, "items");
        insertRecipeIngredient(db, pancakesId, "sugar", 10, "g");
        insertRecipeIngredient(db, pancakesId, "butter", 10, "g");

        long tomatoPastaId = insertRecipe(
                db,
                "Tomato Pasta",
                "Cook the pasta until soft. Chop the tomatoes and cook them in a pan with butter. Add the cooked pasta and mix."
        );

        insertRecipeIngredient(db, tomatoPastaId, "pasta", 100, "g");
        insertRecipeIngredient(db, tomatoPastaId, "tomato", 2, "items");
        insertRecipeIngredient(db, tomatoPastaId, "butter", 10, "g");

        long cheesyPastaId = insertRecipe(
                db,
                "Cheesy Pasta",
                "Cook the pasta until soft. Warm the milk in a pan, add the cheese and stir until melted. Mix with the pasta."
        );

        insertRecipeIngredient(db, cheesyPastaId, "pasta", 100, "g");
        insertRecipeIngredient(db, cheesyPastaId, "cheese", 50, "g");
        insertRecipeIngredient(db, cheesyPastaId, "milk", 100, "ml");

        long mashedPotatoId = insertRecipe(
                db,
                "Mashed Potato",
                "Boil the potatoes until soft. Drain them, add the milk and butter and mash until smooth."
        );

        insertRecipeIngredient(db, mashedPotatoId, "potato", 2, "items");
        insertRecipeIngredient(db, mashedPotatoId, "milk", 50, "ml");
        insertRecipeIngredient(db, mashedPotatoId, "butter", 20, "g");


        long potatoOmeletteId = insertRecipe(
                db,
                "Potato Omelette",
                "Cook the potato until soft and cut it into pieces. Beat the eggs. Melt the butter in a pan, add the potato and eggs and cook until set."
        );

        insertRecipeIngredient(db, potatoOmeletteId, "potato", 1, "items");
        insertRecipeIngredient(db, potatoOmeletteId, "egg", 2, "items");
        insertRecipeIngredient(db, potatoOmeletteId, "butter", 10, "g");
    }

    private long insertRecipe(SQLiteDatabase db, String name, String steps) {

        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("steps", steps);

        return db.insert("recipes", null, values);
    }
    private void insertRecipeIngredient(
            SQLiteDatabase db,
            long recipeId,
            String ingredientName,
            double quantity,
            String unit) {

        ContentValues values = new ContentValues();
        values.put("recipe_id", recipeId);
        values.put("ingredient_name", ingredientName);
        values.put("quantity", quantity);
        values.put("unit", unit);

        db.insert("recipe_ingredients", null, values);
    }
    public ArrayList<Recipe> getAllRecipes() {

        ArrayList<Recipe> recipes = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM recipes ORDER BY name ASC",
                null
        );

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                String steps = cursor.getString(cursor.getColumnIndexOrThrow("steps"));

                Recipe recipe = new Recipe(id, name, steps);
                recipes.add(recipe);

            } while (cursor.moveToNext());
        }

        cursor.close();

        return recipes;
    }
    public ArrayList<RecipeIngredient> getRecipeIngredients(int recipeId) {

        ArrayList<RecipeIngredient> ingredients = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM recipe_ingredients WHERE recipe_id = ?",
                new String[]{String.valueOf(recipeId)}
        );

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                int storedRecipeId = cursor.getInt(cursor.getColumnIndexOrThrow("recipe_id"));
                String ingredientName = cursor.getString(
                        cursor.getColumnIndexOrThrow("ingredient_name")
                );
                double quantity = cursor.getDouble(
                        cursor.getColumnIndexOrThrow("quantity")
                );
                String unit = cursor.getString(
                        cursor.getColumnIndexOrThrow("unit")
                );

                RecipeIngredient ingredient = new RecipeIngredient(
                        id,
                        storedRecipeId,
                        ingredientName,
                        quantity,
                        unit
                );

                ingredients.add(ingredient);

            } while (cursor.moveToNext());
        }

        cursor.close();

        return ingredients;
    }
}
