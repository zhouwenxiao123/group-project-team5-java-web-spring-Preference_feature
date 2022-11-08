package com.example.mealit.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = {"ingredients"})
public class GroceryList implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long groceryListId;

    @CreationTimestamp
    private Date creationDate;

    @LastModifiedDate
    private Date lastModifiedDate;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "ingredient_grocery_list_map",
            joinColumns = @JoinColumn(name = "grocery_list_id", referencedColumnName = "groceryListId"),
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
