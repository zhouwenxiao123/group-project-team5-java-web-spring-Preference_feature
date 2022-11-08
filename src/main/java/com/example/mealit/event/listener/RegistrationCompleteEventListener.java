package com.example.mealit.event.listener;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.example.mealit.entity.User;
import com.example.mealit.event.RegistrationCompleteEvent;
import com.example.mealit.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class RegistrationCompleteEventListener implements 
    ApplicationListener<RegistrationCompleteEvent> {


    @Autowired
    private UserService userService;
 
    @Autowired
    private JavaMailSender javaMailSender;

    private final String sender = "alaap.pv@gmail.com";

    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        // create verification token for user which is then attached to the link that is sent to them
        User user = event.getUser();
        String token = UUID.randomUUID().toString();

        userService.saveVerificationTokenForUser(token, user);

        // then we can send the email to the user

        String url = event.getApplicationUrl() + "/verifyRegistration?token=" + token;

        // log.info("click the link to verify : {}", url);

        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();

            mailMessage.setFrom(sender);
            mailMessage.setTo(user.getEmail());
            mailMessage.setText("Dear " + user.getName() + 
                " please click the verification link to verify your account: " +
                url);
            mailMessage.setSubject("Please Verify your registration");

            javaMailSender.send(mailMessage);
        }catch(Exception e) {
            log.error("error while sending email");
        }
    }
    
}
