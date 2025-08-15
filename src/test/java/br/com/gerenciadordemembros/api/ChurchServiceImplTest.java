//package br.com.gerenciadordemembros.api;
//
//import br.com.gerenciadordemembros.api.dtos.AddressDTO;
//import br.com.gerenciadordemembros.api.dtos.ChurchRequestDTO;
//import br.com.gerenciadordemembros.api.dtos.ChurchResponseDTO;
//import br.com.gerenciadordemembros.api.enums.MinisterialPosition;
//import br.com.gerenciadordemembros.api.model.Member;
//import br.com.gerenciadordemembros.api.model.Minister;
//import br.com.gerenciadordemembros.api.repository.MemberRepository;
//import br.com.gerenciadordemembros.api.repository.MinisterRepository;
//import br.com.gerenciadordemembros.api.service.ChurchService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.DirtiesContext;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.web.server.ResponseStatusException;
//
//import java.time.LocalDate;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//@ActiveProfiles("test")
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
//class ChurchServiceImplTest {
//
//    @Autowired
//    private ChurchService churchService;
//
//    @Autowired
//    private MinisterRepository ministerRepository;
//
//    @Autowired
//    private MemberRepository memberRepository;
//
//    private Minister savedPastor;
//
//    @BeforeEach
//    void setUp() {
//        // Parte 1: Criar o Membro (já está correta)
//        Member pastorMember = new Member();
//        pastorMember.setFullName("Pastor Teste de Setup");
//        pastorMember.setCpf("11122233344");
//        pastorMember.setRg("1234567");
//        pastorMember.setTelephone("83999998888");
//        pastorMember.setDateOfBirth(LocalDate.of(1980, 10, 20));
//        Member savedMember = memberRepository.save(pastorMember);
//
//        // Parte 2: Criar o Ministro (aqui está a correção)
//        Minister pastorMinister = new Minister();
//        pastorMinister.setMember(savedMember); // Associa ao membro que acabamos de salvar
//
//        // Preenche os campos obrigatórios do Ministro antes de salvar
//        pastorMinister.setPosition(MinisterialPosition.SHEPHERD); // Define o cargo
//        pastorMinister.setConsecrationDate(LocalDate.now());   // Define uma data de consagração
//
//        // Agora sim, salva o ministro completo e válido
//        savedPastor = ministerRepository.save(pastorMinister);
//    }
//
//    @Test
//    @DisplayName("Deve registrar uma igreja com sucesso quando os dados são válidos")
//    void registerChurch_WithValidData_ShouldReturnChurchResponseDTO() {
//        // Arrange
//        var addressDTO = new AddressDTO("Rua Teste", "123", null, "Bairro", "Cidade", "Estado", "País", "58000000", "2507507");
//        var registryDTO = new RegistryDTO("CNPJ", "06.660.717/0001-46"); // CNPJ Válido
//        var churchRequest = new ChurchRequestDTO(
//                "Igreja Teste",
//                "Igreja Pentecostal Teste",
//                registryDTO,
//                savedPastor.getId(),
//                LocalDate.now(),
//                addressDTO
//        );
//
//        // Act
//        ChurchResponseDTO result = churchService.registerChurch(churchRequest);
//
//        // Assert
//        assertNotNull(result);
//        assertNotNull(result.id());
//        assertEquals("Igreja Teste", result.name());
//        assertEquals("06.660.717/0001-46", result.registryNumber());
//        assertEquals(savedPastor.getId(), result.pastorLocalId());
//    }
//
//    @Test
//    @DisplayName("Deve lançar exceção ao tentar registrar igreja com CNPJ inválido")
//    void registerChurch_WithInvalidCnpj_ShouldThrowException() {
//        // Arrange
//        var addressDTO = new AddressDTO("Rua Inválida", "000", null, "Bairro", "Cidade", "Estado", "País", "58000000", "2507507");
//        var registryDTO = new RegistryDTO("CNPJ", "11.111.111/1111-11"); // CNPJ Inválido
//        var churchRequest = new ChurchRequestDTO(
//                "Igreja Inválida",
//                "Igreja Pentecostal Inválida",
//                registryDTO,
//                savedPastor.getId(),
//                LocalDate.now(),
//                addressDTO
//        );
//
//        // Act & Assert
//        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
//            churchService.registerChurch(churchRequest);
//        });
//
//        assertEquals(400, exception.getStatusCode().value());
//        assertTrue(exception.getReason().contains("O número de registro (CNPJ/NIF) é inválido."));
//    }
//}