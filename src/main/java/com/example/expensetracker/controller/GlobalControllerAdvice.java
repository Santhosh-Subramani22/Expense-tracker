package com.example.expensetracker.controller;

import com.example.expensetracker.model.UserProfile;
import com.example.expensetracker.service.UserProfileService;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {

    private final UserProfileService profileService;

    public GlobalControllerAdvice(UserProfileService profileService) {
        this.profileService = profileService;
    }

    @ModelAttribute("profile")
    public UserProfile globalProfile() {
        return profileService.getCurrentProfile();
    }
}