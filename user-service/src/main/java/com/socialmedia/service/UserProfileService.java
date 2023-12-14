package com.socialmedia.service;

import com.socialmedia.dto.request.UserUpdateRequestDto;
import com.socialmedia.dto.request.UserSaveRequestDto;
import com.socialmedia.entity.UserProfile;
import com.socialmedia.entity.enums.EStatus;
import com.socialmedia.excepiton.ErrorType;
import com.socialmedia.excepiton.UserManagerException;
import com.socialmedia.manager.IAuthManager;
import com.socialmedia.mapper.IUserMapper;
import com.socialmedia.rabbitmq.model.RegisterModel;
import com.socialmedia.repository.IUserRepository;
import com.socialmedia.util.JWTTokenManager;
import com.socialmedia.util.ServiceManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
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
        UserProfile userProfile = IUserMapper.INSTANCE.toUserProfile(dto);
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

    public String updateUserProfile(UserUpdateRequestDto dto) {
        Optional<Long> authId = tokenManager.getIdFromToken(dto.getToken());
        if (authId.isEmpty()) {
            throw new UserManagerException(ErrorType.INVALID_TOKEN);
        }
        Optional<UserProfile> optionalUserProfile = userRepository.findByAuthId(authId.get());
        UserProfile userProfile = optionalUserProfile.orElseThrow(() -> new UserManagerException(ErrorType.USER_NOT_FOUND));


        //emailini değiştir miyorsa servise istek atmasın
        if (!userProfile.getEmail().equals(dto.getEmail())) {
            userProfile.setEmail(dto.getEmail());
            authManager.update(IUserMapper.INSTANCE.toUpdateRequestDto(userProfile));
        }

        userProfile.setAbout(dto.getAbout() == null ? userProfile.getAbout() : dto.getAbout());
        userProfile.setAddress(dto.getAddress() == null ? userProfile.getAddress() : dto.getAddress());
        userProfile.setPhone(dto.getPhone() == null ? userProfile.getPhone() : dto.getPhone());
        userProfile.setAvatar(dto.getAvatar() == null ? userProfile.getAvatar() : dto.getAvatar());


        update(userProfile);
        return "Güncelleme Başarılı";
    }

    public Long getUserIdfromToken(String token) {
        Long authId = tokenManager.getIdFromToken(token).orElseThrow(() -> new UserManagerException(ErrorType.INVALID_TOKEN));

        Optional<UserProfile> optionalUserProfile = userRepository.findByAuthId(authId);
        UserProfile userProfile = optionalUserProfile.orElseThrow(() -> new UserManagerException(ErrorType.USER_NOT_FOUND));
        return userProfile.getId();
    }

    public void createNewUserWithRabbit(RegisterModel registerModel) {
            UserProfile userProfile = IUserMapper.INSTANCE.toUserProfile(registerModel);
            save(userProfile);
            System.out.println(userProfile);
    }

    @Cacheable(value = "userprofilebyUsername",key = "#username.toLowerCase()")
    public UserProfile findByUsername(String username) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return userRepository.findByUsername(username.toLowerCase()).orElseThrow(() -> new UserManagerException(ErrorType.USER_NOT_FOUND));
    }

    @Cacheable(value = "findbystatus")//ACTIVE, ASTIVE
    public List<UserProfile> findByStatus(EStatus status) {
        List<UserProfile> list =  userRepository.findByStatus(status);
        return list;
    }
}
