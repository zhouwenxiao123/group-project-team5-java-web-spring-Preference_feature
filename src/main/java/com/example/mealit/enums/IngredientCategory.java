package com.example.mealit.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum IngredientCategory {

    Meat("Meat"),
    Vegetables("Vegetables"),
    Seafood("Seafood"),
    DairyProducts("Dairy"),
    ProcessedFood("Processed"),
    RiceGrains("RiceGrains"),
    Fruits("Fruits"),
    Other("Other");


    private final String category;
}
