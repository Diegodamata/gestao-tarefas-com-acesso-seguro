package com.diegodev.taskmanager.controllers.dtos.mappers;

import com.diegodev.taskmanager.controllers.dtos.requests.UserRequestDto;
import com.diegodev.taskmanager.domain.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(UserRequestDto dto);

}
