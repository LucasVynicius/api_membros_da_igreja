package br.com.gerenciadordemembros.api.controller;

import br.com.gerenciadordemembros.api.dtos.MinistroRequestDTO;
import br.com.gerenciadordemembros.api.dtos.MinistroResponseDTO;
import br.com.gerenciadordemembros.api.service.MinistroService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/ministro")
public class MinistroController {

    private final MinistroService ministroService;

    @PostMapping
    public ResponseEntity<MinistroResponseDTO> criaMinistro(@RequestBody MinistroRequestDTO dto){
        MinistroResponseDTO ministroCriado = ministroService.consagrarMinistro(dto);

        return ResponseEntity.status(201).body(ministroCriado);
    }
}
