package br.com.gerenciadordemembros.api.mapper;

import br.com.gerenciadordemembros.api.dtos.MembroRequestDTO;
import br.com.gerenciadordemembros.api.dtos.MembroResponseDTO;
import br.com.gerenciadordemembros.api.model.Membro;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MembroMapper {

    @Mapping(target = "id", ignore = true)
    Membro toEntity(MembroRequestDTO dto);

    MembroResponseDTO toDTO(Membro membro);
}
