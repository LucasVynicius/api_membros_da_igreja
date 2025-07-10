package br.com.gerenciadordemembros.api.mapper;

import br.com.gerenciadordemembros.api.dtos.EnderecoDTO;
import br.com.gerenciadordemembros.api.model.Endereco;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EnderecoMapper {

    Endereco toEntity(EnderecoDTO dto);

    EnderecoDTO toDTO(Endereco endereco);
}
