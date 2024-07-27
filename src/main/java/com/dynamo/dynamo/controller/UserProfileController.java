package com.dynamo.dynamo.controller;

import com.dynamo.dynamo.payload.request.UserProfileDto;
import com.dynamo.dynamo.services.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserProfileController {
    @Autowired
    UserProfileService userProfileService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getProfile(@PathVariable Long id) {
        return ResponseEntity.ok(userProfileService.getProfile(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProfile(@PathVariable Long id,
                                           @RequestBody UserProfileDto userProfileDto) {
        return ResponseEntity.ok(userProfileService.updateProfile(id, userProfileDto));
    }
}
