package br.com.gerenciadordemembros.api.service;

import br.com.gerenciadordemembros.api.dtos.DocumentRequestDTO;
import br.com.gerenciadordemembros.api.model.Church;
import br.com.gerenciadordemembros.api.model.Member;
import br.com.gerenciadordemembros.api.model.Minister;
import br.com.gerenciadordemembros.api.repository.ChurchRepository;
import br.com.gerenciadordemembros.api.repository.MemberRepository;
import br.com.gerenciadordemembros.api.repository.MinisterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {

    private final MemberRepository memberRepository;
    private final ChurchRepository churchRepository;
    private final MinisterRepository ministerRepository;

    @Override
    public byte[] generateRecommendationLetter(DocumentRequestDTO dto) {

        Member member = memberRepository.findById(dto.idMember())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Erro: Membro não encontrado."));

        Church church = member.getChurch();
        if (church == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Igreja não encontrada para este membro.");
        }

        // CORREÇÃO: Pegamos o pastor local, que é quem vai assinar
        Minister pastorLocal = church.getPastorLocal();
        if (pastorLocal == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "O pastor local da igreja não foi definido.");
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy", new Locale("pt", "BR"));
        String formattedDate = LocalDate.now().format(formatter);

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            Document document = new Document();
            PdfWriter.getInstance(document, baos);
            document.open();

            // Fontes para o documento
            Font fontHeader = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
            Font fontBody = FontFactory.getFont(FontFactory.HELVETICA, 10);
            Font fontBold = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10);

            // --- Conteúdo da Carta de Recomendação (Versão Resumida) ---
            document.add(new Paragraph(church.getName(), fontHeader));

            // CORREÇÃO: Usamos a cidade da igreja, não uma cidade fixa
            document.add(new Paragraph(church.getAddress().getCity() + ", " + formattedDate, fontBody));
            document.add(new Paragraph(" "));

            // Parágrafos
            document.add(new Paragraph(
                    new Phrase("A IGREJA GRAÇA E REINO, por meio de seu representante legal, vem por meio desta recomendar, para os devidos fins, o(a) irmão(ã) ", fontBody)
            ));
            document.add(new Phrase(member.getFullName() + ", ", fontBold));
            document.add(new Phrase("portador(a) do RG nº " + member.getRg() + " e do CPF nº " + member.getCpf() + ". ", fontBody));

            document.add(new Paragraph(
                    new Phrase(member.getFullName(), fontBold)
            ));
            document.add(new Phrase(" é membro ativo e fiel de nossa comunidade, participando de forma íntegra e dedicada das atividades e ministérios. Durante seu tempo conosco, demonstrou um caráter alinhado aos princípios da Palavra de Deus, refletindo os valores de nossa igreja, que são fundamentados no amor a Cristo e no serviço ao próximo. Sua conduta sempre foi exemplar, marcada por uma fé ativa e um compromisso genuíno com seu crescimento espiritual.", fontBody));

            document.add(new Paragraph(
                    new Phrase("Reconhecemos nele(a) maturidade espiritual e um coração de servo, pronto para contribuir com a obra do Reino. Portanto, nós o(a) apoiamos e o(a) enviamos com a nossa bênção para [Descrever o propósito], convictos de que será um instrumento de Deus para abençoar vidas e glorificar o nome de nosso Senhor Jesus Cristo.", fontBody)
            ));

            document.add(new Paragraph(
                    new Phrase("Sendo o que se apresenta para o momento, colocamo-nos à disposição para quaisquer esclarecimentos que se façam necessários.", fontBody)
            ));

            // Assinatura do Pastor Local
            document.add(new Paragraph(" "));
            document.add(new Paragraph(pastorLocal.getMember().getFullName(), fontBold));
            document.add(new Paragraph(pastorLocal.getPosition().name(), fontBody));
            document.add(new Paragraph(church.getName(), fontBody));

            document.close();
            return baos.toByteArray();

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao gerar o PDF.", e);
        }
    }
}