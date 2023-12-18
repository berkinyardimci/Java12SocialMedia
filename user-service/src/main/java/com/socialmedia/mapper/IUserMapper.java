package com.socialmedia.mapper;

import com.socialmedia.dto.request.UpdateRequestDto;
import com.socialmedia.dto.request.UserSaveRequestDto;
import com.socialmedia.dto.response.UserProfileResponseDto;
import com.socialmedia.entity.UserProfile;
import com.socialmedia.rabbitmq.model.RegisterElasticModel;
import com.socialmedia.rabbitmq.model.RegisterModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IUserMapper {

    IUserMapper INSTANCE = Mappers.getMapper(IUserMapper.class);

    UserProfile toUserProfile(UserSaveRequestDto dto);
    UserProfile toUserProfile(RegisterModel registerModel);

    @Mapping(source = "authId", target = "id")
    UpdateRequestDto toUpdateRequestDto(UserProfile userProfile);


    @Mapping(source = "id", target = "userProfileId")
    UserProfileResponseDto toUserProfileResponseDto(UserProfile userProfile);

    @Mapping(source = "id",target = "userProfileId")
    RegisterElasticModel toRegisterElasticModel(UserProfile userProfile);
}

