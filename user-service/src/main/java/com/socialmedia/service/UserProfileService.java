package com.socialmedia.service;

import com.socialmedia.dto.request.UpdateUserProfileRequestDto;
import com.socialmedia.dto.request.UserSaveRequestDto;
import com.socialmedia.entity.UserProfile;
import com.socialmedia.entity.enums.EStatus;
import com.socialmedia.excepiton.ErrorType;
import com.socialmedia.excepiton.UserManagerException;
import com.socialmedia.manager.IAuthManager;
import com.socialmedia.mapper.IUserMapper;
import com.socialmedia.repository.IUserRepository;
import com.socialmedia.util.JWTTokenManager;
import com.socialmedia.util.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserProfileService extends ServiceManager<UserProfile, Long> {

    private final IUserRepository userRepository;
    private final JWTTokenManager tokenManager;

    private final IAuthManager authManager;

    public UserProfileService(IUserRepository userRepository, JWTTokenManager tokenManager, IAuthManager authManager) {
        super(userRepository);
        this.userRepository = userRepository;
        this.tokenManager = tokenManager;
        this.authManager = authManager;
    }

    public Boolean createNewUser(UserSaveRequestDto dto) {
        UserProfile userProfile = IUserMapper.INSTANCE.toUserPRofile(dto);
        save(userProfile);
        return true;
    }

    public String acivateUser(Long authId) {
        Optional<UserProfile> userProfile = userRepository.findByAuthId(authId);
        if (userProfile.isEmpty()) {
            throw new UserManagerException(ErrorType.USER_NOT_FOUND);
        }
        userProfile.get().setStatus(EStatus.ACTIVE);
        update(userProfile.get());
        return "Hesap başarıyla aktive edilmiştir";
    }

    public String updateUserProfile(UpdateUserProfileRequestDto dto) {
        Optional<Long> authId = tokenManager.getIdFromToken(dto.getToken());
        if (authId.isEmpty()) {
            throw new UserManagerException(ErrorType.INVALID_TOKEN);
        }
        Optional<UserProfile> userProfile = userRepository.findByAuthId(authId.get());
        if (userProfile.isEmpty()) {
            throw new UserManagerException(ErrorType.USER_NOT_FOUND);
        }
        if (!userProfile.get().getEmail().equals(dto.getEmail()) ||
                !userProfile.get().getUsername().equals(dto.getUsername())) {
            userProfile.get().setEmail(dto.getEmail());
            userProfile.get().setUsername(dto.getUsername());

            authManager.update(IUserMapper.INSTANCE.toUpdateRequestDto(userProfile.get()));
        }

        userProfile.get().setAvatar(dto.getAvatar());
        userProfile.get().setAbout(dto.getAbout());
        userProfile.get().setPhone(dto.getPhone());
        userProfile.get().setAddress(dto.getAddress());
        update(userProfile.get());
        return "Guncelleme başarılı";

    }
}
