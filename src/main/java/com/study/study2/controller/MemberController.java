package com.study.study2.controller;

import com.study.study2.model.Member;
import com.study.study2.model.MemberResponseRecord;
import com.study.study2.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    public ResponseEntity<List<MemberResponseRecord>> getAllMembers() {
        List<Member> allMembers = memberService.getAllMembers();
        List<MemberResponseRecord> responseRecords = allMembers.stream()
                .map(MemberResponseRecord::from)
                .toList();

        return ResponseEntity.ok(responseRecords);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberResponseRecord> getMemberById(@PathVariable Long id) {
        return ResponseEntity.ok(MemberResponseRecord.from(memberService.getMemberById(id)));
    }

}
