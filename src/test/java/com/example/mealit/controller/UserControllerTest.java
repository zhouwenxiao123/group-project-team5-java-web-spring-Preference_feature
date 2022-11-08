package com.example.mealit.controller;


import com.example.mealit.dto.UserDto;
import com.example.mealit.entity.User;
import com.example.mealit.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @MockBean
    private UserService userService;

    @Mock
    private ApplicationEventPublisher publisher;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void loginPageTest()throws Exception{
        this.mockMvc.perform(MockMvcRequestBuilders.get("/login")).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void indexPageTest() throws Exception {
       this.mockMvc.perform(MockMvcRequestBuilders.get("/index")).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void showRegistrationFormTest() throws Exception{
        this.mockMvc.perform(MockMvcRequestBuilders.get("/register"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("user"));
    }

    @Test
    public void registerUserTest() throws Exception{
        UserDto userDto = new UserDto();
        userDto.setFirstName("Kim");
        userDto.setLastName("Namjoon");
        userDto.setEmail("namjoon94@gmail.com");
        userDto.setPassword("1234");

        when(userService.findUserByEmail(anyString())).thenReturn(null);
        when(userService.saveUser(userDto)).thenReturn(new User());

        this.mockMvc.perform(MockMvcRequestBuilders.post("/register/save").with(new RequestPostProcessor() {
                            @Override
                            public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
                                request.setServerName("servername");
                                request.setServerPort(8080);
                                return request;
                            }
                        })
                .flashAttr("user", userDto)).andExpect(MockMvcResultMatchers.model().hasNoErrors())
                .andDo(print())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/register?success"));

        verify(userService).saveUser(userDto);
    }
}
