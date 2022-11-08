package com.example.mealit.dto;

import com.example.mealit.entity.User;
import lombok.Data;

import java.io.Serializable;
@Data
public class PreferenceDto implements Serializable {
    private Boolean eggs;

    private Boolean gluten;

    private Boolean milk;

    private Boolean nuts;

    private Boolean peanuts;

    private Boolean seafood;

    private Boolean shellfish;

    private Boolean soybeans;

    private Boolean vegan;

    private Boolean wheat;

    private User user;
}
