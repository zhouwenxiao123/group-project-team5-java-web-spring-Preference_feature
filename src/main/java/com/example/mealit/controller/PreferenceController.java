package com.example.mealit.controller;

import com.example.mealit.common.R;
import com.example.mealit.dto.PreferenceDto;
import com.example.mealit.entity.Preferences;
import com.example.mealit.entity.User;
import com.example.mealit.service.PreferenceService;
import com.example.mealit.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zwx
 * @date 2022/10/25 19:45
 */
@Controller
@Slf4j
public class PreferenceController {
    @Autowired
    private PreferenceService preferenceService;
    @Autowired
    private UserService userService;

    private final Logger LOGGER =
            LoggerFactory.getLogger(PreferenceController.class);

    @GetMapping("/preference")
    public String preferencePage(Model model){
        LOGGER.info("Accessing preference page");

        PreferenceDto preferenceDto=new PreferenceDto();
        model.addAttribute("preferences.egg",preferenceDto.getEggs());
        model.addAttribute("preferences.gluten",preferenceDto.getGluten());
        model.addAttribute("preferences.milk",preferenceDto.getMilk());
        model.addAttribute("preferences.nuts",preferenceDto.getNuts());
        model.addAttribute("preferences.peanuts",preferenceDto.getPeanuts());
        model.addAttribute("preferences.seafood",preferenceDto.getSeafood());
        model.addAttribute("preferences.shellfish",preferenceDto.getShellfish());
        model.addAttribute("preferences.vegan",preferenceDto.getVegan());




        model.addAttribute("preferences.wheat",preferenceDto.getWheat());
        model.addAttribute("preferences.soybeans",preferenceDto.getSoybeans());

        return "modifyPreference";

    }


   @PostMapping("/preference")
    public R<String> savePreference(PreferenceDto preferenceDto){
        //从SpringSecurity取出登录用户数据
       Authentication authentication =
               SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        if(userDetails!=null){
            //取出登录用户名
            String username = userDetails.getUsername();
            //根据用户名（邮箱）查询DB获取用户
            User user = userService.findUserByEmail(username);

            //先删除，再新增
            preferenceService.deletePreferencesByUserId(user.getUserId());

            //关联User
            preferenceDto.setUser(user);
            preferenceService.insertPreferences(preferenceDto);
            return R.success("successful save!");
        }
        return R.error("用户不存在");
   }
}
