package br.com.gerenciadordemembros.api.controller;

import br.com.gerenciadordemembros.api.dtos.MinisterRequestDTO;
import br.com.gerenciadordemembros.api.dtos.MinisterResponseDTO;
import br.com.gerenciadordemembros.api.service.MinisterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/ministros")
@CrossOrigin(origins = "http://127.0.0.1:5500")
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
}
