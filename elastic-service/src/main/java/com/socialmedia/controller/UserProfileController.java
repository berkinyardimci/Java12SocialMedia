package com.socialmedia.controller;

import com.socialmedia.entity.UserProfile;
import com.socialmedia.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/elastic")
public class UserProfileController {

    private final UserProfileService userProfileService;

    @GetMapping("/findall")
    public ResponseEntity<Iterable<UserProfile>> findAll(){
        return ResponseEntity.ok(userProfileService.findAll());
    }

}
