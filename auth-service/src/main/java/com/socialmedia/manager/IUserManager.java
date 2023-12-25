package com.socialmedia.manager;

import com.socialmedia.dto.request.UserSaveRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(url = "http://localhost:9091/user", decode404 = true ,name = "auth-userprofile")
public interface IUserManager {


    @PostMapping("/save")
    ResponseEntity<Boolean> createNewUser(@RequestBody UserSaveRequestDto dto, @RequestHeader("Authorization") String token);

    @PostMapping("activation/{authId}")
    ResponseEntity<String> activateUser(@PathVariable Long authId);
}
