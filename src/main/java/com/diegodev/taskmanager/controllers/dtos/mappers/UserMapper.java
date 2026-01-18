package com.diegodev.taskmanager.controllers.dtos.mappers;

import com.diegodev.taskmanager.controllers.dtos.reponses.UserResponseDto;
import com.diegodev.taskmanager.controllers.dtos.reponses.UserUpdateResponseDto;
import com.diegodev.taskmanager.controllers.dtos.requests.UserRequestDto;
import com.diegodev.taskmanager.domain.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(UserRequestDto dto);

    UserResponseDto toDto(User user);

    List<UserResponseDto> toListDto(List<User> users);

    UserUpdateResponseDto toUpdateDto(User user);
}
