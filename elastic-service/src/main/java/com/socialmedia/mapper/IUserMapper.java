package com.socialmedia.mapper;

import com.socialmedia.dto.response.UserProfileResponseDto;
import com.socialmedia.entity.UserProfile;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IUserMapper {

    IUserMapper INSTANCE = Mappers.getMapper(IUserMapper.class);

    List<UserProfile> toUserProfile(List<UserProfileResponseDto> list);
}
