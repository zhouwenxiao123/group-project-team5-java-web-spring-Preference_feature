package com.example.mealit.service;

import com.example.mealit.dto.PreferenceDto;
import com.example.mealit.entity.Preferences;

/**
 * @author zwx
 * @date 2022/10/25 20:37
 */
public interface PreferenceService {

    /**
     * add new preferences
     * @param preferenceDto
     * @return
     */
    Preferences insertPreferences(PreferenceDto preferenceDto);

    /**
     * delete preferences
     * @param preferencesId
     */
    void deletePreferences(Long preferencesId);


    void deletePreferencesByUserId(Long userId);
}
