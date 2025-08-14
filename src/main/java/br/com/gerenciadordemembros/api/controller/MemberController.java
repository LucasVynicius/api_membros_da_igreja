package br.com.gerenciadordemembros.api.controller;

import br.com.gerenciadordemembros.api.dtos.MemberRequestDTO;
import br.com.gerenciadordemembros.api.dtos.MemberResponseDTO;
import br.com.gerenciadordemembros.api.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('ADMIN', 'SECRETARY')")
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<MemberResponseDTO> createMember(@RequestBody @Valid MemberRequestDTO dto){
        MemberResponseDTO memberCreated = memberService.registerMember(dto);
        return ResponseEntity.status(201).body(memberCreated);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberResponseDTO> getMemberById(@PathVariable Long id){
        MemberResponseDTO member = memberService.getMemberById(id);
        return ResponseEntity.ok(member);
    }

    @GetMapping
    public ResponseEntity<List<MemberResponseDTO>> getAllMembers(){
        return ResponseEntity.ok(memberService.searchAllMember());
    }

    @PutMapping("/{id}")
    public ResponseEntity<MemberResponseDTO> updateMember(@PathVariable Long id, @Valid @RequestBody MemberRequestDTO dto){
        MemberResponseDTO updatedMember = memberService.updateMember(id, dto);
        return ResponseEntity.ok(updatedMember);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long id){
        memberService.deleteMember(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/photo")
    public ResponseEntity<MemberResponseDTO> uploadMemberPhoto(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file) {

        MemberResponseDTO updatedMember = memberService.uploadPhoto(id, file);

        return ResponseEntity.ok(updatedMember);
    }
}