package br.com.gerenciadordemembros.api.mapper;

import br.com.gerenciadordemembros.api.dtos.IgrejaRequestDTO;
import br.com.gerenciadordemembros.api.dtos.IgrejaResponseDTO;
import br.com.gerenciadordemembros.api.model.Igreja;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IgrejaMapper {

    @Mapping(target = "id", ignore = true)
    Igreja toEntity(IgrejaRequestDTO dto);

    @Mapping(source = "nomeFantasia", target = "nomeFantasia")
    @Mapping(source = "endereco.cidade", target = "cidade")
    @Mapping(source = "endereco.pais", target = "pais")
    IgrejaResponseDTO toDTO(Igreja igreja);

}
