package com.example.mealit.repository;

import com.example.mealit.entity.SavedRecipes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SavedRecipesRepository extends JpaRepository<SavedRecipes, Long> {
}
