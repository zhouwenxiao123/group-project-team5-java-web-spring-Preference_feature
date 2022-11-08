package com.example.mealit.service.impl;

import com.example.mealit.dto.PreferenceDto;
import com.example.mealit.entity.Preferences;
import com.example.mealit.repository.PreferencesRepository;
import com.example.mealit.service.PreferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zwx
 * @date 2022/10/25 20:37
 */
@Service
public class PreferenceServiceImpl implements PreferenceService {
    @Autowired
    PreferencesRepository preferencesRepository;


    @Override
    public Preferences insertPreferences(PreferenceDto preferenceDto) {
       Preferences preferences=new Preferences();
       preferences.setGluten(preferenceDto.getGluten());
       preferences.setEggs(preferenceDto.getEggs());
       preferences.setSeafood(preferenceDto.getSeafood());
       preferences.setMilk(preferenceDto.getMilk());
       preferences.setPeanuts(preferenceDto.getMilk());
       preferences.setShellfish(preferenceDto.getShellfish());
       preferences.setSoybeans(preferenceDto.getSoybeans());
       preferences.setUser(preferenceDto.getUser());
       preferencesRepository.save(preferences);
       return preferences;
    }

    @Override
    public void deletePreferences(Long preferencesId) {
        preferencesRepository.deleteById(preferencesId);
    }

    @Override
    public void deletePreferencesByUserId(Long userId) {
        preferencesRepository.deleteByUserId(userId);
    }


}
