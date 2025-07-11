package br.com.gerenciadordemembros.api.mapper;

import br.com.gerenciadordemembros.api.dtos.MinistroRequestDTO;
import br.com.gerenciadordemembros.api.dtos.MinistroResponseDTO;
import br.com.gerenciadordemembros.api.model.Ministro;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MinistroMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "membro", ignore = true)
    @Mapping(target = "igreja", ignore = true)
    Ministro toEntity(MinistroRequestDTO dto);

    @Mapping(source = "membro.id", target = "idMembro")
    @Mapping(source = "membro.nomeCompleto", target = "nomeCompleto")
    @Mapping(source = "membro.cpf", target = "cpf")
    @Mapping(source = "membro.telefone", target = "telefone")
    @Mapping(source = "membro.email", target = "email")
    @Mapping(source = "igreja.id", target = "idIgreja")
    @Mapping(source = "igreja.nome", target = "igrejaNome")
    @Mapping(source = "igreja.nomeFantasia", target = "igrejaNomeFantasia")
    @Mapping(source = "igreja.endereco.cidade", target = "igrejaCidade")
    @Mapping(source = "igreja.endereco.pais", target = "igrejaPais")
    @Mapping(source = "igreja.endereco", target = "enderecoIgreja")
    MinistroResponseDTO toDTO(Ministro ministro);
}
