package br.com.gerenciadordemembros.api.mapper;

import br.com.gerenciadordemembros.api.dtos.AddressDTO;
import br.com.gerenciadordemembros.api.model.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    @Mapping(target = "id", ignore = true)
    Address toEntity(AddressDTO dto);

    AddressDTO toDTO(Address address);
}
