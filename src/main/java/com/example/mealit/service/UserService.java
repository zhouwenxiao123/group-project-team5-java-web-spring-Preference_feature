package com.example.mealit.service;

import java.util.List;
import java.util.Optional;

import com.example.mealit.dto.UserDto;
import com.example.mealit.entity.User;

public interface UserService {

    User saveUser(UserDto userDto);

    List<UserDto> findAllUsers();

    Optional<User> findById(long id);

    User findUserByEmail(String email);

    void delete(User user);

    void saveVerificationTokenForUser(String token, User user);

    String validateVerificationToken(String token);

}
