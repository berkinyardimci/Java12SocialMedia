package com.socialmedia.mapper;

import com.socialmedia.dto.request.UserSaveRequestDto;
import com.socialmedia.dto.response.LoginResponse;
import com.socialmedia.dto.response.RegisterResponse;
import com.socialmedia.entity.Auth;
import com.socialmedia.rabbitmq.model.MailSenderModel;
import com.socialmedia.rabbitmq.model.RegisterModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IAuthMapper {

    IAuthMapper INSTANCE = Mappers.getMapper(IAuthMapper.class);


    LoginResponse toLoginResponse(Auth auth);

    @Mapping(source = "id", target = "authId")
    UserSaveRequestDto toUserSaveRequestDto(Auth auth);

    RegisterResponse toRegisterResponse(Auth auth);

    @Mapping(source = "id", target = "authId")
    RegisterModel toRegisterModel(Auth auth);

    MailSenderModel toMailSenderModel(Auth auth);
}
