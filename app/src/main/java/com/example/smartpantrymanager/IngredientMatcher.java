package com.example.smartpantrymanager;

import java.util.ArrayList;
import java.util.Locale;

public class IngredientMatcher {

    public static boolean canMakeRecipe(
            ArrayList<PantryItem> pantryItems,
            ArrayList<RecipeIngredient> requiredIngredients) {

        for (RecipeIngredient requiredIngredient : requiredIngredients) {

            String requiredName = normalizeIngredientName(
                    requiredIngredient.getIngredientName()
            );

            String requiredUnit = normalizeUnit(
                    requiredIngredient.getUnit()
            );

            double totalAvailableQuantity = 0;

            for (PantryItem pantryItem : pantryItems) {

                String pantryName = normalizeIngredientName(
                        pantryItem.getName()
                );

                String pantryUnit = normalizeUnit(
                        pantryItem.getUnit()
                );

                if (requiredName.equals(pantryName)) {

                    if (areUnitsCompatible(requiredUnit, pantryUnit)) {

                        double convertedQuantity = convertQuantity(
                                pantryItem.getQuantity(),
                                pantryUnit,
                                requiredUnit
                        );

                        totalAvailableQuantity += convertedQuantity;
                    }
                }
            }

            if (totalAvailableQuantity < requiredIngredient.getQuantity()) {
                return false;
            }
        }

        return true;
    }

    private static String normalizeIngredientName(String name) {

        if (name == null) {
            return "";
        }

        String normalized = name
                .trim()
                .toLowerCase(Locale.ROOT)
                .replace("-", " ")
                .replaceAll("\\s+", " ");

        if (normalized.endsWith("atoes")) {
            normalized = normalized.substring(0, normalized.length() - 2);
        } else if (normalized.endsWith("ies") && normalized.length() > 3) {
            normalized = normalized.substring(0, normalized.length() - 3) + "y";
        } else if (normalized.endsWith("s")
                && !normalized.endsWith("ss")
                && normalized.length() > 1) {

            normalized = normalized.substring(0, normalized.length() - 1);
        }

        return normalized;
    }

    private static String normalizeUnit(String unit) {

        if (unit == null) {
            return "";
        }

        String normalized = unit.trim().toLowerCase(Locale.ROOT);

        switch (normalized) {

            case "gram":
            case "grams":
            case "g":
                return "g";

            case "kilogram":
            case "kilograms":
            case "kg":
                return "kg";

            case "millilitre":
            case "millilitres":
            case "milliliter":
            case "milliliters":
            case "ml":
                return "ml";

            case "litre":
            case "litres":
            case "liter":
            case "liters":
            case "l":
                return "l";

            case "item":
            case "items":
            case "piece":
            case "pieces":
            case "each":
                return "items";

            case "slice":
            case "slices":
                return "slices";

            default:
                return normalized;
        }
    }

    private static boolean areUnitsCompatible(String requiredUnit, String pantryUnit) {

        if (isWeightUnit(requiredUnit) && isWeightUnit(pantryUnit)) {
            return true;
        }

        if (isLiquidUnit(requiredUnit) && isLiquidUnit(pantryUnit)) {
            return true;
        }

        if (requiredUnit.equals("items") && pantryUnit.equals("items")) {
            return true;
        }

        if (requiredUnit.equals("slices") && pantryUnit.equals("slices")) {
            return true;
        }

        return requiredUnit.equals(pantryUnit);
    }

    private static boolean isWeightUnit(String unit) {
        return unit.equals("g") || unit.equals("kg");
    }

    private static boolean isLiquidUnit(String unit) {
        return unit.equals("ml") || unit.equals("l");
    }

    private static double convertQuantity(
            double quantity,
            String pantryUnit,
            String requiredUnit) {

        if (pantryUnit.equals(requiredUnit)) {
            return quantity;
        }

        if (pantryUnit.equals("kg") && requiredUnit.equals("g")) {
            return quantity * 1000;
        }

        if (pantryUnit.equals("g") && requiredUnit.equals("kg")) {
            return quantity / 1000;
        }

        if (pantryUnit.equals("l") && requiredUnit.equals("ml")) {
            return quantity * 1000;
        }

        if (pantryUnit.equals("ml") && requiredUnit.equals("l")) {
            return quantity / 1000;
        }

        return quantity;
    }
}