package com.diegodev.taskmanager.controllers.mappers.role;

import com.diegodev.taskmanager.controllers.dtos.role.requests.RoleRequestDto;
import com.diegodev.taskmanager.controllers.dtos.role.responses.RoleResponseDto;
import com.diegodev.taskmanager.domain.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    Role toEntity(RoleRequestDto roleRequestDto);

    RoleResponseDto toDto(Role role);
}
