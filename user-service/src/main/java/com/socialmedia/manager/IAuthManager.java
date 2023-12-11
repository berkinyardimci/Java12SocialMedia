package com.socialmedia.manager;

import com.socialmedia.dto.request.UpdateRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@FeignClient(url = "http://localhost:9090/auth", decode404 = true ,name = "user-auth")
public interface IAuthManager {


    @PutMapping("/update")
    ResponseEntity<String> update(@RequestBody @Valid UpdateRequestDto dto);


}
