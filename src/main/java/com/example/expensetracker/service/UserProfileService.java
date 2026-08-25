package com.example.expensetracker.service;

import com.example.expensetracker.model.UserProfile;
import com.example.expensetracker.repository.UserProfileRepository;
import org.springframework.stereotype.Service;

@Service
public class UserProfileService {

    private final UserProfileRepository profileRepository;

    public UserProfileService(UserProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public UserProfile getCurrentProfile() {
        return profileRepository.findAll().stream().findFirst().orElseGet(() -> 
            profileRepository.save(new UserProfile("Ranjith", "ranjith@gmail.com", "Personal Account", "INR", true, true))
        );
    }

    public UserProfile updateProfile(UserProfile updated) {
        UserProfile current = getCurrentProfile();
        current.setFullName(updated.getFullName());
        current.setEmail(updated.getEmail());
        current.setRole(updated.getRole() != null ? updated.getRole() : "Personal Account");
        current.setCurrency(updated.getCurrency());
        current.setEmailNotifications(updated.isEmailNotifications());
        current.setMonthlyReportAlert(updated.isMonthlyReportAlert());
        return profileRepository.save(current);
    }
}