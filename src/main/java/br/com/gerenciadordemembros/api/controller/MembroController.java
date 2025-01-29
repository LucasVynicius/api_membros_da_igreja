package br.com.gerenciadordemembros.api.controller;

import br.com.gerenciadordemembros.api.dtos.MembroRequestDTO;
import br.com.gerenciadordemembros.api.dtos.MembroResponseDTO;
import br.com.gerenciadordemembros.api.service.MembroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/membros")
public class MembroController {

    @Autowired
    MembroService membroService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MembroResponseDTO criarMembro(@RequestBody MembroRequestDTO membroRequestDTO){
        return membroService.criarMembro(membroRequestDTO);
    }
}
