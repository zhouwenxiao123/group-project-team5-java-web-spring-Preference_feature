package com.example.mealit.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.mealit.dto.UserDto;
import com.example.mealit.entity.User;
import com.example.mealit.event.RegistrationCompleteEvent;
import com.example.mealit.service.UserService;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ApplicationEventPublisher publisher;

    private final Logger LOGGER =
            LoggerFactory.getLogger(UserController.class);

    // handler method to handle login request
    @GetMapping("/login")
    public String login(){
        LOGGER.info("Accessing logging page");
        return "login";
    }

    // handler method to handle home page request
    @GetMapping("/index")
    public String home(){
        LOGGER.info("Accessing home page");
        return "index";
    }

    // handler method to handle user registration form request
    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        // create model object to store form data
        LOGGER.info("Registration form");
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "register";
    }

    // handler method to handle user registration form submit request
    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto userDto,
                               BindingResult result,
                               Model model,
                               final HttpServletRequest request){
        User existingUser = userService.findUserByEmail(userDto.getEmail());

        if(existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()){
            result.rejectValue("email", null,
                    "There is already an account registered with the same email");
        }

        if(result.hasErrors()){
            model.addAttribute("user", userDto);
            return "/register";
        }
 
        User user = userService.saveUser(userDto);
        String url = applicationUrl(request);
        publisher.publishEvent(new RegistrationCompleteEvent(user, url));
        return "redirect:/register?success";
    }

    @GetMapping("/verify?success")
    public String verifySuccess() {
        return "index";
    }

    @GetMapping("/verifyRegistration")
    public String verifyRegistration(@RequestParam("token") String token) {
        String result = userService.validateVerificationToken(token);

        if(result.equalsIgnoreCase("valid")) {
            return "redirect:/login?verified";
        }
        return "redirect:/login?notverified";
    }

    private String applicationUrl(HttpServletRequest request) {
        return "http://" + 
                request.getServerName() +
                ":" + 
                request.getServerPort() +
                request.getContextPath();
    }

    // handler method to handle list of users
    @GetMapping("/users")
    public String users(Model model){
        List<UserDto> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "users";
    }


    // handler method to handle deleting a user
    @GetMapping ("/users/delete/{email}")
    public String deleteUser(@PathVariable("email") String email,
                             @ModelAttribute("user") UserDto userDto,
                             BindingResult result,
                             Model model) {

        User existingUser = userService.findUserByEmail(userDto.getEmail());
        userService.delete(existingUser);
        return "redirect:/users";
    }

    @GetMapping ("/recipe-display")
    public String recipeResults(Model model) {
        return "recipe-display";
    }


//    // handler method to handle deleting a user
//    @GetMapping("users/delete/{id}")
//    public String deleteUser(@PathVariable("id") long id, Model model) {
//        User user = (User) userService.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
//        model.addAttribute("user", user);
//        userService.delete(user);
//        return "redirect:/users";
//    }

//    @GetMapping("/signup")
//    public String showSignUpForm(User user) {
//        return "add-user";
//    }

//    @PostMapping("/adduser")
//    public String addUser(@Valid User user, BindingResult result, Model model) {
//        if (result.hasErrors()) {
//            return "register";
//        }
//
//        userService.save(user);
//        return "redirect:/index";
//    }
//
//    @GetMapping("/index")
//    public String showUserList(Model model) {
//        model.addAttribute("users", userService.findAllUsers());
//        return "index";
//    }

//    @GetMapping("/edit/{id}")
//    public String showUpdateForm(@PathVariable("id") long id, Model model) {
//        User user = (User) userService.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
//
//        model.addAttribute("user", user);
//        return "update-user";
//    }

//    @PostMapping("/update/{id}")
//    public String updateUser(@PathVariable("id") long id, @Valid User user,
//                             BindingResult result, Model model) {
//        if (result.hasErrors()) {
//            user.setUserId(id);
//            return "update-user";
//        }
//
//        userService.save(user);
//        return "redirect:/index";
//    }



    // additional CRUD methods
}
