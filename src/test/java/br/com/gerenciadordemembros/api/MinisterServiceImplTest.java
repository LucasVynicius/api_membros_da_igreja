package br.com.gerenciadordemembros.api;

import br.com.gerenciadordemembros.api.dtos.MinisterRequestDTO;
import br.com.gerenciadordemembros.api.dtos.MinisterResponseDTO;
import br.com.gerenciadordemembros.api.enums.MinisterialPosition;
import br.com.gerenciadordemembros.api.model.Church;
import br.com.gerenciadordemembros.api.model.Member;
import br.com.gerenciadordemembros.api.repository.ChurchRepository;
import br.com.gerenciadordemembros.api.repository.MemberRepository;
import br.com.gerenciadordemembros.api.service.MinisterService;
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
class MinisterServiceImplTest {

    @Autowired
    private MinisterService ministerService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ChurchRepository churchRepository;

    private Member savedMember;
    private Church savedChurch;

    @BeforeEach
    void setUp() {
        // 1. Criar e salvar uma Igreja base para os testes
        Church church = new Church();
        church.setName("Igreja de Teste para Ministros");
        church.setTradeName("ITM");
        savedChurch = churchRepository.save(church);

        // 2. Criar e salvar um Membro base para ser consagrado
        Member member = new Member();
        member.setFullName("Membro a ser Consagrado");
        member.setCpf("99988877766");
        member.setRg("9876543");
        member.setTelephone("83911112222");
        member.setDateOfBirth(LocalDate.of(1992, 5, 25));
        member.setChurch(savedChurch);
        savedMember = memberRepository.save(member);
    }

    @Test
    @DisplayName("Deve consagrar um membro com sucesso quando os dados são válidos")
    void consecrateMinister_WithValidData_ShouldSucceed() {
        // Arrange (Preparação)
        var requestDto = new MinisterRequestDTO(
                MinisterialPosition.MISSIONARY,
                LocalDate.now(),
                savedMember.getId(),
                savedChurch.getId()
        );

        // Act (Ação)
        MinisterResponseDTO result = ministerService.consecrateMinister(requestDto);

        // Assert (Verificação)
        assertNotNull(result);
        assertNotNull(result.id());
        assertEquals(MinisterialPosition.MISSIONARY, result.position());
        assertEquals(savedMember.getId(), result.idMember());
        assertEquals(savedMember.getFullName(), result.fullName());
    }

    @Test
    @DisplayName("Deve lançar exceção 400 ao tentar consagrar um membro que já é ministro")
    void consecrateMinister_ForExistingMinister_ShouldThrowBadRequest() {
        // Arrange
        // 1. Consagrar o membro uma vez (deve funcionar)
        var firstRequest = new MinisterRequestDTO(
                MinisterialPosition.SHEPHERD,
                LocalDate.now().minusYears(1),
                savedMember.getId(),
                savedChurch.getId()
        );
        ministerService.consecrateMinister(firstRequest);

        // 2. Tentar consagrar o MESMO membro novamente
        var secondRequest = new MinisterRequestDTO(
                MinisterialPosition.DEACON, // Mesmo que seja para outro cargo
                LocalDate.now(),
                savedMember.getId(),
                savedChurch.getId()
        );

        // Act & Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            ministerService.consecrateMinister(secondRequest);
        });

        assertEquals(400, exception.getStatusCode().value());
        assertTrue(exception.getReason().contains("Este membro já possui um cargo ministerial"));
    }

    @Test
    @DisplayName("Deve lançar exceção 404 ao tentar consagrar um membro que não existe")
    void consecrateMinister_WithNonExistentMember_ShouldThrowNotFound() {
        // Arrange
        var requestDto = new MinisterRequestDTO(
                MinisterialPosition.EVANGELIST,
                LocalDate.now(),
                999L, // ID de um membro que não existe
                savedChurch.getId()
        );

        // Act & Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            ministerService.consecrateMinister(requestDto);
        });

        assertEquals(404, exception.getStatusCode().value());
        assertTrue(exception.getReason().contains("Membro não encontrado"));
    }
}