package com.socialmedia.controller;

import com.socialmedia.dto.request.UserUpdateRequestDto;
import com.socialmedia.dto.request.UserSaveRequestDto;
import com.socialmedia.entity.UserProfile;
import com.socialmedia.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
    public ResponseEntity<String> update(@RequestBody @Valid UserUpdateRequestDto dto){
        return  ResponseEntity.ok(userProfileService.updateUserProfile(dto));
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<UserProfile>> findAll(){
        return  ResponseEntity.ok(userProfileService.findAll());
    }

    @GetMapping("/getUserIdfromToken/{token}")
    public Long getUserIdfromToken(@PathVariable String token){
        return  userProfileService.getUserIdfromToken(token);
    }
}
