package br.com.gerenciadordemembros.api.controller;

import br.com.gerenciadordemembros.api.dtos.ChurchRequestDTO;
import br.com.gerenciadordemembros.api.dtos.ChurchResponseDTO;
import br.com.gerenciadordemembros.api.service.ChurchService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/igrejas")
@RequiredArgsConstructor
public class ChurchController {

    private final ChurchService churchService;

    @PostMapping
    public ResponseEntity<ChurchResponseDTO> createChurch(@Valid @RequestBody ChurchRequestDTO dto){
        ChurchResponseDTO igrejaCriada = churchService.registerChurch(dto);

        return ResponseEntity.status(201).body(igrejaCriada);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChurchResponseDTO> searchForChurch(@PathVariable Long id){
        return ResponseEntity.ok(churchService.searchChurchById(id));
    }

    @GetMapping
    public ResponseEntity<List<ChurchResponseDTO>> listarIgrejas(){
        return ResponseEntity.ok(churchService.searchAllChurches());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChurchResponseDTO> atualizarIgreja(@PathVariable Long id, @RequestBody ChurchRequestDTO dto){
        return ResponseEntity.ok(churchService.updateChurch(id, dto));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteChurch(@PathVariable Long id){
        churchService.deleteChurch(id);
        return ResponseEntity.noContent().build();
    }
}
