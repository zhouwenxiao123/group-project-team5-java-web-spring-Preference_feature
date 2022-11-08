package com.example.mealit.event;

import org.springframework.context.ApplicationEvent;

import com.example.mealit.entity.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationCompleteEvent extends ApplicationEvent{

    public RegistrationCompleteEvent(User user, String applicationUrl) {
        super(user);
        this.user = user;
        this.applicationUrl = applicationUrl;
    }
    private User user;
    private String applicationUrl;
    
}
