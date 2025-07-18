package br.com.gerenciadordemembros.api.controller;

import br.com.gerenciadordemembros.api.dtos.IgrejaRequestDTO;
import br.com.gerenciadordemembros.api.dtos.IgrejaResponseDTO;
import br.com.gerenciadordemembros.api.service.IgrejaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/igrejas")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class IgrejaController {

    private final IgrejaService igrejaService;

    @PostMapping
    public ResponseEntity<IgrejaResponseDTO> criar(@Valid @RequestBody IgrejaRequestDTO dto){
        IgrejaResponseDTO igrejaCriada = igrejaService.criarIgreja(dto);

        return ResponseEntity.status(201).body(igrejaCriada);
    }

    @GetMapping("/{id}")
    public ResponseEntity<IgrejaResponseDTO> buscarIgreja(@PathVariable Long id){
        return ResponseEntity.ok(igrejaService.buscarIgrejaPeloId(id));
    }

    @GetMapping
    public ResponseEntity<List<IgrejaResponseDTO>> listarIgrejas(){
        return ResponseEntity.ok(igrejaService.BuscarTodasIgrejas());
    }

    @PutMapping("/{id}")
    public ResponseEntity<IgrejaResponseDTO> atualizarIgreja(@PathVariable Long id, @RequestBody IgrejaRequestDTO dto){
        return ResponseEntity.ok(igrejaService.atualizarIgreja(id, dto));
    }
}
