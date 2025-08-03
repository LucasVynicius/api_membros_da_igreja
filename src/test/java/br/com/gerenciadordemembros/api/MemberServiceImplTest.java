package br.com.gerenciadordemembros.api;

import br.com.gerenciadordemembros.api.dtos.AddressDTO;
import br.com.gerenciadordemembros.api.dtos.MemberRequestDTO;
import br.com.gerenciadordemembros.api.dtos.MemberResponseDTO;
import br.com.gerenciadordemembros.api.model.Church;
import br.com.gerenciadordemembros.api.model.Member;
import br.com.gerenciadordemembros.api.repository.ChurchRepository;
import br.com.gerenciadordemembros.api.repository.MemberRepository;
import br.com.gerenciadordemembros.api.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class MemberServiceImplTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private ChurchRepository churchRepository;

    @Autowired
    private MemberRepository memberRepository;

    private Church savedChurch;

    @BeforeEach
    void setUp() {
        // Antes de cada teste, precisamos de uma igreja válida no banco para associar os membros.
        Church church = new Church();
        church.setName("Igreja Matriz de Teste");
        church.setTradeName("IMT");
        // Preencha outros campos obrigatórios de Church se houver
        savedChurch = churchRepository.save(church);
    }

    @Test
    @DisplayName("Deve registrar um membro com sucesso quando os dados são válidos")
    void registerMember_WithValidData_ShouldSucceed() {
        // Arrange (Preparação)
        var addressDto = new AddressDTO("Rua do Membro", "10", null, "Bairro Teste", "Cidade Teste", "Estado", "País", "58000123", null);
        var requestDto = new MemberRequestDTO(
                "Fulano de Tal",
                "11122233344", // CPF Válido
                "1234567",
                "83988776655",
                "fulano@email.com",
                LocalDate.of(1990, 1, 15),
                null, // Data de batismo opcional
                LocalDate.now(),
                true,
                addressDto,
                savedChurch.getId() // Associa com a igreja criada no setUp
        );

        // Act (Ação)
        MemberResponseDTO result = memberService.registerMember(requestDto);

        // Assert (Verificação)
        assertNotNull(result);
        assertNotNull(result.id());
        assertEquals("Fulano de Tal", result.fullName());
        assertEquals("11122233344", result.cpf());
        assertEquals(savedChurch.getId(), result.idChurch());
        assertTrue(result.active());
    }

    @Test
    @DisplayName("Deve lançar exceção 404 ao tentar registrar membro em igreja inexistente")
    void registerMember_WithNonExistentChurch_ShouldThrowNotFoundException() {
        // Arrange
        var addressDto = new AddressDTO("Rua do Membro", "10", null, "Bairro Teste", "Cidade Teste", "Estado", "País", "58000123", null);
        var requestDto = new MemberRequestDTO(
                "Ciclano de Tal", "22233344455", "7654321", "83911223344",
                "ciclano@email.com", LocalDate.of(1995, 2, 20), null, LocalDate.now(),
                true, addressDto, 999L // ID de uma igreja que não existe
        );

        // Act & Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            memberService.registerMember(requestDto);
        });

        assertEquals(404, exception.getStatusCode().value());
        assertTrue(exception.getReason().contains("Igreja não encontrada"));
    }

    @Test
    @DisplayName("Deve lançar exceção 400 ao tentar registrar membro com CPF duplicado")
    void registerMember_WithDuplicateCpf_ShouldThrowBadRequestException() {
        // Arrange
        // 1. Criar um membro inicial para já existir o CPF no banco
        Member existingMember = new Member();
        existingMember.setFullName("Beltrano Original");
        existingMember.setCpf("33344455566");
        existingMember.setRg("1112223");
        existingMember.setTelephone("83955667788");
        existingMember.setDateOfBirth(LocalDate.of(1985, 3, 25));
        existingMember.setChurch(savedChurch);
        memberRepository.save(existingMember);

        // 2. Preparar um DTO com o MESMO CPF
        var addressDto = new AddressDTO("Rua Outra", "20", null, "Bairro Novo", "Cidade Teste", "Estado", "País", "58000456", null);
        var requestDto = new MemberRequestDTO(
                "Beltrano Cópia", "33344455566", // CPF DUPLICADO
                "3332221", "83988889999", "copia@email.com",
                LocalDate.of(2000, 4, 30), null, LocalDate.now(),
                true, addressDto, savedChurch.getId()
        );

        // Act & Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            memberService.registerMember(requestDto);
        });

        assertEquals(400, exception.getStatusCode().value());
        assertTrue(exception.getReason().contains("CPF já cadastrado"));
    }
}