package br.com.gerenciadordemembros.api.controller;

import br.com.gerenciadordemembros.api.dtos.MembroRequestDTO;
import br.com.gerenciadordemembros.api.dtos.MembroResponseDTO;
import br.com.gerenciadordemembros.api.model.Membro;
import br.com.gerenciadordemembros.api.service.MembroService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/membros")
@RequiredArgsConstructor
public class MembroController {


    private final MembroService membroService;

    @PostMapping
    public ResponseEntity<MembroResponseDTO> criarMembro(@RequestBody MembroRequestDTO dto){
        MembroResponseDTO membroCriado = membroService.criarMembro(dto);
        return ResponseEntity.status(201).body(membroCriado);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<MembroResponseDTO> buscarMembroPeloId(@PathVariable Long id){
        return ResponseEntity.ok(membroService.buscarMembroPeloId(id));
    }

    @GetMapping
    public ResponseEntity<List<MembroResponseDTO>> buscarTodosMembros(){
        return ResponseEntity.ok(membroService.buscarTodosMembros());
    }

    @PutMapping("/{id}")
    public ResponseEntity<MembroResponseDTO> atualizarMembro(@PathVariable Long id, @RequestBody MembroRequestDTO dto){
        return ResponseEntity.ok(membroService.atualizarMembro(id, dto));
    }
}
