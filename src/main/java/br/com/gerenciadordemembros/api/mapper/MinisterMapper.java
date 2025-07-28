package br.com.gerenciadordemembros.api.mapper;

import br.com.gerenciadordemembros.api.dtos.MinisterRequestDTO;
import br.com.gerenciadordemembros.api.dtos.MinisterResponseDTO;
import br.com.gerenciadordemembros.api.model.Minister;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MinisterMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "member", ignore = true)   // estava "membro"
    @Mapping(target = "church", ignore = true)   // estava "igreja"
    Minister toEntity(MinisterRequestDTO dto);


    @Mapping(source = "member.id", target = "idMember")
    @Mapping(source = "member.fullName", target = "fullName")
    @Mapping(source = "member.cpf", target = "cpf")
    @Mapping(source = "member.telephone", target = "telephone")
    @Mapping(source = "member.email", target = "email")


    @Mapping(source = "church.id", target = "idChurch")
    @Mapping(source = "church.name", target = "churchName")
    @Mapping(source = "church.tradeName", target = "churchTradeName")
    @Mapping(source = "church.address.city", target = "churchCity")
    @Mapping(source = "church.address.country", target = "churchCounty")
    @Mapping(source = "church.address", target = "addressChurch")

    MinisterResponseDTO toDTO(Minister minister);
}
