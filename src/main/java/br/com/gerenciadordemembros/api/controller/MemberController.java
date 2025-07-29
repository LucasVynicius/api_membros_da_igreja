package br.com.gerenciadordemembros.api.controller;

import br.com.gerenciadordemembros.api.dtos.MemberRequestDTO;
import br.com.gerenciadordemembros.api.dtos.MemberResponseDTO;
import br.com.gerenciadordemembros.api.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/membros")
@RequiredArgsConstructor
public class MemberController {


    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<MemberResponseDTO> createMember(@RequestBody @Valid MemberRequestDTO dto){
        MemberResponseDTO membrerCreated= memberService.registerMember(dto);
        return ResponseEntity.status(201).body(membrerCreated);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberResponseDTO> MemberById(@PathVariable Long id){
        return ResponseEntity.ok(memberService.searchMemberById(id));
    }

    @GetMapping
    public ResponseEntity<List<MemberResponseDTO>> allMembers(){
        return ResponseEntity.ok(memberService.searchAllMember());
    }

    @PutMapping("/{id}")
    public ResponseEntity<MemberResponseDTO> updateMember(@PathVariable Long id, @Valid @RequestBody MemberRequestDTO dto){
        return ResponseEntity.ok(memberService.updateMember(id, dto));
    }
}
