package br.com.gerenciadordemembros.api.mapper;

import br.com.gerenciadordemembros.api.dtos.ChurchRequestDTO;
import br.com.gerenciadordemembros.api.dtos.ChurchResponseDTO;
import br.com.gerenciadordemembros.api.model.Church;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ChurchMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "pastorLocal", ignore = true)
    @Mapping(target = "members", ignore = true)
    Church toEntity(ChurchRequestDTO dto);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "tradeName", target = "tradeName")
    @Mapping(source = "address.city", target = "city")
    @Mapping(source = "address.country", target = "country")
    @Mapping(source = "foundationDate", target = "foundationDate")
    @Mapping(source = "pastorLocal.id", target = "pastorLocalId")
    @Mapping(source = "pastorLocal.member.fullName", target = "pastorLocalName")
    @Mapping(source = "address", target = "address")
    ChurchResponseDTO toDTO(Church church);

}
