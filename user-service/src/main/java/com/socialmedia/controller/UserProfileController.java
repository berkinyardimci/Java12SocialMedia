package com.socialmedia.controller;

import com.socialmedia.dto.request.UserUpdateRequestDto;
import com.socialmedia.dto.request.UserSaveRequestDto;
import com.socialmedia.dto.response.UserProfileResponseDto;
import com.socialmedia.entity.UserProfile;
import com.socialmedia.entity.enums.EStatus;
import com.socialmedia.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;
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

    /*
    @GetMapping("/getUserIdfromToken/{token}")
    public Long getUserIdfromToken(@PathVariable String token){
        return  userProfileService.getUserIdfromToken(token);
    }
     */

    @GetMapping("/findByUserName/{username}")
    public ResponseEntity<UserProfile> findByUserName(@PathVariable String username){
        return ResponseEntity.ok(userProfileService.findByUsername(username));
    }

    @GetMapping("/findbystatus")
    public ResponseEntity<List<UserProfile>> findByStatus(@RequestParam EStatus status){
        return ResponseEntity.ok(userProfileService.findByStatus(status));
    }

    @GetMapping("/findall/forelastic")
    public ResponseEntity<List<UserProfileResponseDto>> findAllForElasticService(){
        return ResponseEntity.ok(userProfileService.findAllForElasticService());
    }
    @GetMapping("/findallbypageable")
    public ResponseEntity<Page<UserProfile>> findAllByPageable(int pageSize, int pageNumber,@RequestParam(required = false, defaultValue = "ASC") String direction, @RequestParam(required = false, defaultValue = "id")  String sortParameter){
        return ResponseEntity.ok(userProfileService.findAllByPageable(pageSize,pageNumber,direction,sortParameter));
    }

    @GetMapping("/findallbyslice")
    public ResponseEntity<Slice<UserProfile>> findAllBySlice(int pageSize, int pageNumber, @RequestParam(required = false, defaultValue = "ASC") String direction, @RequestParam(required = false, defaultValue = "id")  String sortParameter){
        return ResponseEntity.ok(userProfileService.findAllBySlice(pageSize,pageNumber,direction,sortParameter));
    }
}
