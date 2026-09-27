# Smart Pantry Manager

Smart Pantry Manager is an Android application designed to help users manage ingredients stored in their pantry and discover recipes they can make using the ingredients they already have.

The application was developed in Java using Android Studio and stores its data locally using SQLite.

## Features

- Add pantry ingredients
- View saved pantry ingredients
- Edit existing ingredients
- Delete ingredients
- Store ingredient quantities and measurement units
- Add expiry dates using a date picker
- Save pantry information locally using SQLite
- Recipe database containing 20 recipes
- Suggest recipes based on available pantry ingredients
- Check whether enough quantity of each ingredient is available
- Support basic plural ingredient matching such as `Egg` and `Eggs`
- Support measurement conversions between:
    - grams and kilograms
    - millilitres and litres
- Display a message when no recipes match the pantry
- View recipe ingredients and preparation steps
- Bottom navigation between Pantry, Recipes and Settings
- Save user settings using SharedPreferences
- Allow the user to enable or disable the expiry alert preference
- Allow the user to select a preferred measurement unit option

## Technologies Used

- Java
- Android Studio
- XML
- SQLite
- RecyclerView
- SharedPreferences
- Material Components
- Git
- GitHub

## Main Screens

### Pantry
The Pantry screen displays all ingredients currently stored by the user. Users can add, edit and delete pantry items.

### Add/Edit Ingredient
The ingredient form allows the user to enter:

- Ingredient name
- Quantity
- Measurement unit
- Expiry date

The same screen is used when editing an existing ingredient.

### Suggested Recipes
The Suggested Recipes screen compares the user's pantry with the ingredients required by each recipe.

A recipe is only suggested when all required ingredients are available in sufficient quantities.

### Recipe Details
The Recipe Detail screen displays:

- Recipe name
- Required ingredients and quantities
- Preparation instructions

### Settings
The Settings screen allows the user to save preferences for:

- Expiry alerts
- Preferred measurement units

The settings are stored using SharedPreferences and remain saved after the application is closed.

## Recipe Matching

The application uses strict recipe matching.

For a recipe to appear as available:

1. Every required ingredient must exist in the pantry.
2. The available quantity must be equal to or greater than the required quantity.
3. Compatible measurement units are converted before quantities are compared.
4. Basic ingredient name variations such as singular and plural forms are handled.

For example, if a recipe requires two eggs and the pantry only contains one egg, the recipe will not be suggested.

## Database

The application uses an SQLite database called:

`SmartPantry.db`

The main database tables are:

- `pantry_items`
- `recipes`
- `recipe_ingredients`

The pantry table stores the user's ingredients, while the recipe tables contain recipe information and the ingredients required for each recipe.

## Testing

The application was tested on a physical Samsung Android device.

Testing included:

- Creating ingredients
- Editing ingredients
- Deleting ingredients
- Checking data persistence
- Testing the expiry date picker
- Testing recipe quantity requirements
- Testing singular and plural ingredient names
- Testing unit conversion
- Testing insufficient ingredient quantities
- Testing the no-recipe-match message
- Opening recipe details
- Testing bottom navigation
- Testing saved Settings preferences

The project also successfully completes an Android Studio build.

## How to Run the Project

1. Clone or download this repository.
2. Open the project in Android Studio.
3. Allow Gradle to finish syncing.
4. Connect an Android device or start an emulator.
5. Run the application using the Run button in Android Studio.

## Author

Tia Brijlal

Bachelor of Science in Information Technology