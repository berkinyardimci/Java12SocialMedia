package com.socialmedia.util;

import com.socialmedia.dto.response.UserProfileResponseDto;
import com.socialmedia.manager.IUserManager;
import com.socialmedia.mapper.IUserMapper;
import com.socialmedia.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
@RequiredArgsConstructor
public class GetAllData {

    private final IUserManager userManager;

    private final UserProfileService userProfileService;

    //Kullanıcı register olduğunda veriyi elastige atıcaz

    //@PostConstruct
    public void initData(){
        List<UserProfileResponseDto> list = userManager.findAllForElasticService().getBody();
        userProfileService.saveAll(IUserMapper.INSTANCE.toUserProfile(list));
    }

}
