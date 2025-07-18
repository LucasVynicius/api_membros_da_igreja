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

    @Mapping(source = "igreja.id", target = "idIgreja")
    @Mapping(source = "igreja.nome", target = "igrejaNome", defaultValue = "")
    @Mapping(source = "igreja.nomeFantasia", target = "igrejaNomeFantasia", defaultValue = "")
    @Mapping(source = "igreja.endereco.cidade", target = "igrejaCidade", defaultValue = "")
    @Mapping(source = "igreja.endereco.pais", target = "igrejaPais", defaultValue = "")
    MembroResponseDTO toDTO(Membro membro);
}
