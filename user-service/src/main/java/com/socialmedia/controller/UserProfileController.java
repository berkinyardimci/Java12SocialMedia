package com.socialmedia.controller;

import com.socialmedia.dto.request.UpdateRequestDto;
import com.socialmedia.dto.request.UpdateUserProfileRequestDto;
import com.socialmedia.dto.request.UserSaveRequestDto;
import com.socialmedia.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserProfileController {

    private final UserProfileService userProfileService;

    @PostMapping("/save")
    public ResponseEntity<Boolean> createNewUser(@RequestBody UserSaveRequestDto dto){

        return ResponseEntity.ok(userProfileService.createNewUser(dto));
    }

    @PostMapping("activation/{authId}")
    public  ResponseEntity<String> activateUser(@PathVariable Long authId){
        return  ResponseEntity.ok(userProfileService.acivateUser(authId));
    }

    @PutMapping("/update")
    public ResponseEntity<String> update(@RequestBody @Valid UpdateUserProfileRequestDto dto){
        return  ResponseEntity.ok(userProfileService.updateUserProfile(dto));
    }
}
