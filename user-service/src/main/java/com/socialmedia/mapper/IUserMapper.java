package com.socialmedia.mapper;

import com.socialmedia.dto.request.UpdateRequestDto;
import com.socialmedia.dto.request.UserSaveRequestDto;
import com.socialmedia.entity.UserProfile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IUserMapper {

    IUserMapper INSTANCE = Mappers.getMapper(IUserMapper.class);

    UserProfile toUserPRofile(UserSaveRequestDto dto);

    @Mapping(source = "authId", target = "id")
    UpdateRequestDto toUpdateRequestDto(UserProfile userProfile);


}
