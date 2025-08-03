package br.com.gerenciadordemembros.api.mapper;

import br.com.gerenciadordemembros.api.dtos.UserRequestDTO;
import br.com.gerenciadordemembros.api.dtos.UserResponseDTO;
import br.com.gerenciadordemembros.api.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "enabled", ignore = true)
    User toEntity(UserRequestDTO dto);


    UserResponseDTO toDTO(User user);

}