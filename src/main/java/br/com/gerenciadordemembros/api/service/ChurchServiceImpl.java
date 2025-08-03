package br.com.gerenciadordemembros.api.service;

import br.com.gerenciadordemembros.api.dtos.ChurchRequestDTO;
import br.com.gerenciadordemembros.api.dtos.ChurchResponseDTO;
import br.com.gerenciadordemembros.api.mapper.AddressMapper;
import br.com.gerenciadordemembros.api.mapper.ChurchMapper;
import br.com.gerenciadordemembros.api.model.Address;
import br.com.gerenciadordemembros.api.model.Church;
import br.com.gerenciadordemembros.api.model.Minister;
import br.com.gerenciadordemembros.api.model.Registry;
import br.com.gerenciadordemembros.api.repository.AddressRepository;
import br.com.gerenciadordemembros.api.repository.ChurchRepository;
import br.com.gerenciadordemembros.api.repository.MinisterRepository;
import br.com.gerenciadordemembros.api.repository.RegistryRepository;
import br.com.gerenciadordemembros.api.validator.RegistryValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChurchServiceImpl implements ChurchService {

    private final ChurchRepository churchRepository;
    private final ChurchMapper churchMapper;
    private final RegistryRepository registryRepository;
    private final MinisterRepository ministerRepository;
    private final AddressMapper addressMapper;
    private final AddressRepository addressRepository;

    @Override
    @Transactional
    public ChurchResponseDTO registerChurch(ChurchRequestDTO dto) {

        if (!RegistryValidator.isValid(dto.registry())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O número de registro (CNPJ/NIF) é inválido.");
        }

        if (registryRepository.existsByRegistryNumber(dto.registry().registryNumber())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Já existe uma igreja com este número de registro.");
        }

        Minister pastor = ministerRepository.findById(dto.pastorLocalId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pastor local não encontrado."));

        Address addressEntity = addressMapper.toEntity(dto.address());
        Address savedAddress = addressRepository.save(addressEntity);

        Registry registryEntity = new Registry(null, dto.registry().registryType(), dto.registry().registryNumber(), null);
        Registry savedRegistry = registryRepository.save(registryEntity);

        Church church = churchMapper.toEntity(dto);
        church.setPastorLocal(pastor);
        church.setAddress(savedAddress);
        church.setRegistry(savedRegistry);

        Church savedChurch = churchRepository.save(church);

        return churchMapper.toDTO(savedChurch);
    }

    @Override
    public ChurchResponseDTO searchChurchById(Long id) {
        Church church = churchRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Erro: Igreja não encontrada. Verifique se o ID informado está correto e tente novamente."));
        return churchMapper.toDTO(church);
    }

    @Override
    public List<ChurchResponseDTO> searchAllChurches() {
        return churchRepository.findAll().stream().map(churchMapper::toDTO).toList();
    }

    @Override
    @Transactional
    public ChurchResponseDTO updateChurch(Long id, ChurchRequestDTO dto) {

        if (!RegistryValidator.isValid(dto.registry())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O número de registro (CNPJ/NIF) é inválido.");
        }

        Church existingChurch = churchRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Erro: Igreja não encontrada."));


        Minister pastor = ministerRepository.findById(dto.pastorLocalId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pastor local não encontrado."));

        if (!existingChurch.getRegistry().getRegistryNumber().equals(dto.registry().registryNumber())) {
            if (registryRepository.existsByRegistryNumber(dto.registry().registryNumber())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Já existe uma igreja com este número de registro.");
            }
        }

        existingChurch.setName(dto.name());
        existingChurch.setTradeName(dto.tradeName());
        existingChurch.setFoundationDate(dto.foundationDate());
        existingChurch.setPastorLocal(pastor);

        existingChurch.getRegistry().setRegistryType(dto.registry().registryType());
        existingChurch.getRegistry().setRegistryNumber(dto.registry().registryNumber());
        addressMapper.updateAddressFromDto(dto.address(), existingChurch.getAddress());



        Church updatedChurch = churchRepository.save(existingChurch);

        return churchMapper.toDTO(updatedChurch);
    }

    @Transactional
    @Override
    public void deleteChurch(Long id) {
        if (!churchRepository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Erro: Igreja não encontrada. Verifique se o ID informado está correto e tente novamente.");
        }
        churchRepository.deleteById(id);
    }
}