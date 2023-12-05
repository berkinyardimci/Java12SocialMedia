package com.socialmedia.controller;

import com.socialmedia.dto.request.UserSaveRequestDto;
import com.socialmedia.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserProfileController {

    private final UserProfileService userProfileService;

    @PostMapping("/save")
    public ResponseEntity<Boolean> createNewUser(@RequestBody UserSaveRequestDto dto){

        return ResponseEntity.ok(userProfileService.createNewUser(dto));
    }
}
