package br.com.gerenciadordemembros.api.mapper;

import br.com.gerenciadordemembros.api.dtos.MemberRequestDTO;
import br.com.gerenciadordemembros.api.dtos.MemberResponseDTO;
import br.com.gerenciadordemembros.api.model.Member;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {AddressMapper.class})
public interface MemberMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "church", ignore = true)
    Member toEntity(MemberRequestDTO dto);

    @Mapping(source = "church.id", target = "idChurch")
    @Mapping(source = "church.name", target = "churchName", defaultValue = "")
    @Mapping(source = "church.tradeName", target = "churchTradeName", defaultValue = "")
    @Mapping(source = "church.address.city", target = "churchCity", defaultValue = "")
    @Mapping(source = "church.address.country", target = "churchCounty", defaultValue = "")
    MemberResponseDTO toDTO(Member member);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "church", ignore = true)
    void updateMemberFromDto(MemberRequestDTO dto, @MappingTarget Member member);
}
