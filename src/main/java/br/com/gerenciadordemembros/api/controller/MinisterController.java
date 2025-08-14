package br.com.gerenciadordemembros.api.controller;

import br.com.gerenciadordemembros.api.dtos.MinisterRequestDTO;
import br.com.gerenciadordemembros.api.dtos.MinisterResponseDTO;
import br.com.gerenciadordemembros.api.service.MinisterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/ministers")
public class MinisterController {

    private final MinisterService ministerService;

    @PostMapping
    public ResponseEntity<MinisterResponseDTO> createMinister(@RequestBody @Valid MinisterRequestDTO dto){
        MinisterResponseDTO ministerCreate = ministerService.consecrateMinister(dto);

        return ResponseEntity.status(201).body(ministerCreate);
    }

    @GetMapping("{id}")
    public ResponseEntity<MinisterResponseDTO> ministerById(@PathVariable Long id){
        return ResponseEntity.ok(ministerService.searchMinisterById(id));
    }

    @GetMapping
    public ResponseEntity<List<MinisterResponseDTO>> listMinister(){
        return ResponseEntity.ok(ministerService.listMinister());
    }

    @PutMapping("/{id}")
    public ResponseEntity<MinisterResponseDTO> updateMinister(@PathVariable Long id, @RequestBody @Valid MinisterRequestDTO dto){
        return ResponseEntity.ok(ministerService.updateMinister(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMinisterId(@PathVariable Long id){
        ministerService.deleteMinister(id);
        return ResponseEntity.noContent().build();
    }
}
