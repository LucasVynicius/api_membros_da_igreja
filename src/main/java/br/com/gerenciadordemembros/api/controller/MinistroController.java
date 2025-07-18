package br.com.gerenciadordemembros.api.controller;

import br.com.gerenciadordemembros.api.dtos.MinistroRequestDTO;
import br.com.gerenciadordemembros.api.dtos.MinistroResponseDTO;
import br.com.gerenciadordemembros.api.service.MinistroService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/ministros")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class MinistroController {

    private final MinistroService ministroService;

    @PostMapping
    public ResponseEntity<MinistroResponseDTO> criaMinistro(@RequestBody MinistroRequestDTO dto){
        MinistroResponseDTO ministroCriado = ministroService.consagrarMinistro(dto);

        return ResponseEntity.status(201).body(ministroCriado);
    }
}
