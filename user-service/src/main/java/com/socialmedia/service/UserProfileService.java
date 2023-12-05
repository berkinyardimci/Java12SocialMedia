package com.socialmedia.service;

import com.socialmedia.dto.request.UserSaveRequestDto;
import com.socialmedia.entity.UserProfile;
import com.socialmedia.mapper.IUserMapper;
import com.socialmedia.repository.IUserRepository;
import com.socialmedia.util.ServiceManager;
import org.springframework.stereotype.Service;

@Service
public class UserProfileService extends ServiceManager<UserProfile,Long> {

    private final IUserRepository userRepository;

    public UserProfileService(IUserRepository userRepository) {
        super(userRepository);
        this.userRepository = userRepository;
    }

    public Boolean createNewUser(UserSaveRequestDto dto) {
        UserProfile userProfile = IUserMapper.INSTANCE.toUserPRofile(dto);
        save(userProfile);
        return true;
    }
}
