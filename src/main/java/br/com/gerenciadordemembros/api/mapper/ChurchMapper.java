package br.com.gerenciadordemembros.api.mapper;

import br.com.gerenciadordemembros.api.dtos.ChurchRequestDTO;
import br.com.gerenciadordemembros.api.dtos.ChurchResponseDTO;
import br.com.gerenciadordemembros.api.model.Church;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ChurchMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "pastorLocal", ignore = true)
    @Mapping(target = "members", ignore = true)
    Church toEntity(ChurchRequestDTO dto);

    @Mapping(source = "registry.registryType", target = "registryType")
    @Mapping(source = "registry.registryNumber", target = "registryNumber")
    @Mapping(source = "pastorLocal.id", target = "pastorLocalId")
    @Mapping(source = "pastorLocal.member.fullName", target = "pastorLocalName")
    ChurchResponseDTO toDTO(Church church);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "pastorLocal", ignore = true)
    @Mapping(target = "members", ignore = true)
    @Mapping(target = "address", ignore = true)
    @Mapping(target = "registry", ignore = true)
    void updateChurchFromDto(ChurchRequestDTO dto, @MappingTarget Church church);
}
