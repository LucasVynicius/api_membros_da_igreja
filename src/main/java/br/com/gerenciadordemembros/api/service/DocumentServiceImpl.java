package br.com.gerenciadordemembros.api.service;

import br.com.gerenciadordemembros.api.dtos.DocumentRequestDTO;
import br.com.gerenciadordemembros.api.model.Church;
import br.com.gerenciadordemembros.api.model.Member;
import br.com.gerenciadordemembros.api.model.Minister;
import br.com.gerenciadordemembros.api.repository.ChurchRepository;
import br.com.gerenciadordemembros.api.repository.MemberRepository;
import br.com.gerenciadordemembros.api.repository.MinisterRepository;
import com.itextpdf.text.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

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

        Minister pastorLocal = church.getPastorLocal();
        if (pastorLocal == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "O pastor local da igreja não foi definido.");
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy", new Locale("pt", "BR"));
        String formattedDate = LocalDate.now().format(formatter);
        String pastorPosition = pastorLocal.getPosition().getLabelPtBr(); // Supondo que você tenha um método para o nome em português

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            Document document = new Document(PageSize.A4, 85, 56, 85, 56);
            PdfWriter.getInstance(document, baos);
            document.open();

            // Fontes para o documento
            Font fontHeader = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, BaseColor.BLACK);
            Font fontBody = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.BLACK);
            Font fontBold = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.BLACK);

            // --- Conteúdo da Carta de Recomendação ---

            // Logo e nome da igreja no topo
            Image logo = Image.getInstance("src/main/resources/logo.png"); // Substitua pelo caminho correto da sua logo
            logo.scaleToFit(50, 50);
            logo.setAlignment(Element.ALIGN_CENTER);
            document.add(logo);

            Paragraph churchName = new Paragraph(church.getName(), fontHeader);
            churchName.setAlignment(Element.ALIGN_CENTER);
            document.add(churchName);
            document.add(new Paragraph(" "));

            // Cidade e Data
            Paragraph cityAndDate = new Paragraph(church.getAddress().getCity() + ", " + formattedDate, fontBody);
            cityAndDate.setAlignment(Element.ALIGN_RIGHT);
            document.add(cityAndDate);
            document.add(new Paragraph(" "));

            // Assunto da carta
            Paragraph subject = new Paragraph(" CARTA RECOMENDAÇÃO", fontBold);
            subject.setAlignment(Element.ALIGN_CENTER);
            document.add(subject);
            document.add(new Paragraph(" "));

            // Parágrafo de saudação
            document.add(new Paragraph(
                    "Aos amados irmãos em Cristo, ministros e líderes do Corpo de Cristo,",
                    fontBody
            ));

            document.add(new Paragraph(" "));

            document.add(new Paragraph(
                    "A graça e a paz de nosso Senhor Jesus Cristo sejam convosco.",
                    fontBody
            ));
            document.add(new Paragraph(" "));

            // Corpo da carta
            Paragraph bodyParagraph = new Paragraph();
            bodyParagraph.setFont(fontBody);
            bodyParagraph.add("O(A) irmão(a) ");
            bodyParagraph.add(new Chunk(member.getFullName(), fontBold));
            bodyParagraph.add(", portador(a) do CPF ");
            bodyParagraph.add(new Chunk(member.getCpf(), fontBold));
            bodyParagraph.add(" e RG ");
            bodyParagraph.add(new Chunk(member.getRg(), fontBold));
            bodyParagraph.add(" é membro(a) em plena comunhão e ordem da nossa igreja, a ");
            bodyParagraph.add(new Chunk(church.getName(), fontBold));
            bodyParagraph.add(", desde ");
            bodyParagraph.add(new Chunk(member.getEntryDate() != null ? member.getEntryDate().format(formatter) : "data não informada", fontBold));
            document.add(new Paragraph(" "));

            bodyParagraph.add(". Tendo em vista o(a) seu(sua) propósito, por meio desta carta, o(a) recomendamos a todos os que a lerem, para que o(a) recebam com alegria e lhe prestem toda a assistência espiritual e moral de que precisar.\n\n");
            document.add(bodyParagraph);
            document.add(new Paragraph(" "));

            // Encerramento
            document.add(new Paragraph(
                    "Declaramos que o(a) irmão(a) é digno(a) de toda confiança e nosso sincero afeto, pois sua conduta cristã e sua fé são um testemunho vivo do evangelho de Cristo.",
                    fontBody
            ));
            document.add(new Paragraph(" "));

            Paragraph name = new Paragraph(pastorLocal.getMember().getFullName(), fontBody);
            document.add(new Paragraph(
                    "Em Cristo, com amor e apreço, "  ,
                    fontBody
            ));
            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));

            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));

            // Assinatura do Pastor Local
            LineSeparator line = new LineSeparator();
            line.setLineColor(BaseColor.BLACK);
            line.setLineWidth(1f);
            document.add(line);

            Paragraph signatureName = new Paragraph(pastorLocal.getMember().getFullName(), fontBold);
            signatureName.setAlignment(Element.ALIGN_CENTER);
            document.add(signatureName);

            Paragraph signaturePosition = new Paragraph(pastorPosition, fontBody);
            signaturePosition.setAlignment(Element.ALIGN_CENTER);
            document.add(signaturePosition);

            Paragraph signatureChurch = new Paragraph(church.getName(), fontBody);
            signatureChurch.setAlignment(Element.ALIGN_CENTER);
            document.add(signatureChurch);

            document.close();
            return baos.toByteArray();

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao gerar o PDF.", e);
        }
    }
}