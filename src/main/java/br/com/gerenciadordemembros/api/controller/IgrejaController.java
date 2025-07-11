package br.com.gerenciadordemembros.api.controller;

import br.com.gerenciadordemembros.api.dtos.IgrejaRequestDTO;
import br.com.gerenciadordemembros.api.dtos.IgrejaResponseDTO;
import br.com.gerenciadordemembros.api.service.IgrejaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/igreja")
@RequiredArgsConstructor
public class IgrejaController {

    private final IgrejaService igrejaService;

    @PostMapping
    public ResponseEntity<IgrejaResponseDTO> criar(@RequestBody IgrejaRequestDTO dto){
        IgrejaResponseDTO igrejaCriada = igrejaService.criarIgreja(dto);

        return ResponseEntity.status(201).body(igrejaCriada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<IgrejaResponseDTO> atualizarIgreja(@PathVariable Long id, @RequestBody IgrejaRequestDTO dto){
        return ResponseEntity.ok(igrejaService.atualizarIgreja(id, dto));
    }
}
