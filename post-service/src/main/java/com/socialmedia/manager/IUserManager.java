package com.socialmedia.manager;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url = "http://localhost:9091/user", decode404 = true ,name = "post-user")
public interface IUserManager {


    @GetMapping("/getUserIdfromToken/{token}")
    Long getUserIdfromToken(@PathVariable String token);
}
