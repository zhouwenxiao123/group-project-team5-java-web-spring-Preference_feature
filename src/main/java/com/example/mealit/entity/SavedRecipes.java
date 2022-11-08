package com.example.mealit.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SavedRecipes implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long savedRecipeId;
    @Column(nullable = false)
    private String recipeDisplayName;
    @Column
    private String recipeDisplayDescription;
    @Column
    private int cookingTime;
    @Column
    private int calories;

    @Lob
    private Byte[] image;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "recipe_ingredient_map",
            joinColumns = @JoinColumn(name = "saved_recipe_id", referencedColumnName = "savedRecipeId"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id", referencedColumnName = "ingredientId")

    )
    private List<Ingredient> ingredients;

    public void addIngredients(Ingredient ingredient){
        if(ingredients == null){
            ingredients = new ArrayList<>();
        }
        ingredients.add(ingredient);
    }
}
