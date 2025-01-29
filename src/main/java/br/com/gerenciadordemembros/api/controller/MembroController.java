package br.com.gerenciadordemembros.api.controller;

import br.com.gerenciadordemembros.api.dtos.MembroRequestDTO;
import br.com.gerenciadordemembros.api.dtos.MembroResponseDTO;
import br.com.gerenciadordemembros.api.model.Membro;
import br.com.gerenciadordemembros.api.service.MembroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Membro buscarMembroPeloId(@PathVariable Long id){
        return membroService.buscarMembroPeloId(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Membro> buscarTodosMembros(){
        return membroService.buscarTodosMembros();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Membro atualizarMembro(@PathVariable Long id, @RequestBody MembroRequestDTO membroRequestDTO){
        return membroService.atualizarMembro(id, membroRequestDTO);
    }
}
