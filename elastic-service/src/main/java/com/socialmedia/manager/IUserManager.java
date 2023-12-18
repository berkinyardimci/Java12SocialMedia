package com.socialmedia.manager;

import com.socialmedia.dto.response.UserProfileResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(url = "http://localhost:9091/user", decode404 = true ,name = "elastic-userporfile")
public interface IUserManager {

    @GetMapping("/findall/forelastic")
    ResponseEntity<List<UserProfileResponseDto>> findAllForElasticService();
}
