package com.example.mealit.repository;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.mealit.entity.User;


public class UserRepositoryTest extends BaseTest{

    @Autowired
    private UserRepository userRepository;

    @Test
    public void saveUser(){
        User user = User.builder().name("anabelle11").password("password").email("anabelle21@gmail.com")
                .phoneNumber(8004400680L).build();
        userRepository.save(user);

        userRepository.findById(user.getUserId()).ifPresent(savedUser -> {
            assertThat(savedUser).isNotNull();
            assertEquals(user.getEmail(), savedUser.getEmail());
            assertEquals(user.getPhoneNumber(), savedUser.getPhoneNumber());
            assertEquals(user.getName(), savedUser.getName());
        });
    }

    @Test
    public void findByEmail(){
        User user = User.builder().name("taehyung").password("password").email("taehyung95@gmail.com")
                .phoneNumber(8004400680L).build();
        userRepository.save(user);

        User savedUser = userRepository.findByEmail("taehyung95@gmail.com");
        assertThat(savedUser).isNotNull();
        assertEquals(user.getEmail(), savedUser.getEmail());
    }

    @Test
    public void testQuery(){
        userRepository.findByEmail("wxzhou@bu.edu");
    }
}
