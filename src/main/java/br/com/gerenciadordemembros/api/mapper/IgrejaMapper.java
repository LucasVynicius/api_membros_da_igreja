package br.com.gerenciadordemembros.api.mapper;

import br.com.gerenciadordemembros.api.dtos.IgrejaRequestDTO;
import br.com.gerenciadordemembros.api.dtos.IgrejaResponseDTO;
import br.com.gerenciadordemembros.api.model.Igreja;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IgrejaMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "pastorLocal", ignore = true)
    @Mapping(target = "membros", ignore = true)
    Igreja toEntity(IgrejaRequestDTO dto);

    @Mapping(source = "nomeFantasia", target = "nomeFantasia")
    @Mapping(source = "endereco.cidade", target = "cidade")
    @Mapping(source = "endereco.pais", target = "pais")
    @Mapping(source = "pastorLocal.id", target = "pastorLocalId")
    @Mapping(source = "pastorLocal.membro.nomeCompleto", target = "pastorLocalNome")
    IgrejaResponseDTO toDTO(Igreja igreja);

}
