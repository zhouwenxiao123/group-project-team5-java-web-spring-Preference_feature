package com.example.mealit.repository;

import com.example.mealit.entity.Preferences;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PreferencesRepository extends JpaRepository<Preferences, Long> {
    @Query(value = "delete from preferences where user_id = ?1",nativeQuery = true)
    @Modifying
    @Transactional
    void deleteByUserId(Long userId);
}
