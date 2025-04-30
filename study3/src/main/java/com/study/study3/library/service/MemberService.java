package com.study.study3.library.service;

import com.study.study3.library.dto.MemberRequestRecord;
import com.study.study3.library.exception.MemberNotFoundException;
import com.study.study3.library.model.Member;
import com.study.study3.library.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    /**
     * 모든 회원 조회
     */
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    /**
     * 특정 ID 로 회원 조회
     */
    public Member getMemberById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new MemberNotFoundException(id));
    }

    /**
     * 특정 Email 로 회원 조회
     */
    public Member getMemberByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberNotFoundException(email));
    }

    /**
     * keyword 로 회원 검색
     */
    public List<Member> getMemberByKeyword(String keyword) {
        return memberRepository.findByKeyword(keyword);
    }

    /**
     * 회원 저장 (생성/수정)
     */
    public Member createMember(MemberRequestRecord request) {
        memberRepository.findByEmail(request.email())
                .ifPresent(member -> {
                    throw new IllegalArgumentException("Member with email " + request.email() + " already exists");
        });

        Member member = request.toEntity();
        return memberRepository.save(member);
    }

    /**
     * 회원 삭제
     */
    public boolean deleteMember(Long id) {
        return memberRepository.delete(id);
    }
}
