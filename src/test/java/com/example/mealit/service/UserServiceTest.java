package com.example.mealit.service;

import com.example.mealit.dto.UserDto;
import com.example.mealit.entity.User;
import com.example.mealit.entity.VerificationToken;
import com.example.mealit.repository.RoleRepository;
import com.example.mealit.repository.UserRepository;
import com.example.mealit.repository.VerificationTokenRepository;
import com.example.mealit.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private VerificationTokenRepository verificationTokenRepository;

    private User user;

    @BeforeEach
    public void setup(){
        user = getUser("Jeon JungKook","jungkook97@gmail.com", 5157083418L);
    }

    @DisplayName("JUnit test for findAllUsers method")
    @Test
    public void findAllUsers_shouldReturnAllUsers(){
        List<User> users = Arrays.asList(user);
        Mockito.when(userRepository.findAll()).thenReturn(users);
        List<UserDto> allUsers = userService.findAllUsers();
        Mockito.verify(userRepository).findAll();
        assertThat(allUsers.size()).isEqualTo(users.size());
    }

    @DisplayName("JUnit test for delete method")
    @Test
    public void deleteUser_shouldDeleteUser(){
        userService.delete(user);
        Mockito.verify(userRepository).delete(user);
    }

    @DisplayName("JUnit test for  saveVerificationTokenForUser method")
    @Test
    public void saveVerificationTokenForUser_shouldSaveVerificationToken(){
        ArgumentCaptor<VerificationToken> captor = ArgumentCaptor.forClass(VerificationToken.class);
        String token = "Token";
        userService.saveVerificationTokenForUser(token, user);
        Mockito.verify(verificationTokenRepository).save(captor.capture());
        VerificationToken savedVerificationToken = captor.getValue();
        assertEquals(token, savedVerificationToken.getToken());
        assertEquals(user, savedVerificationToken.getUser());
    }

    private User getUser(String name, String email, Long phoneNumber){
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        return user;
    }

}
