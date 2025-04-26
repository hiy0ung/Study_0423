package com.study.study2.service;

import com.study.study2.exception.MemberNotFoundException;
import com.study.study2.model.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {
    private MemberService memberService;

    @BeforeEach
    public void setUp() {
        memberService = new MemberService();
    }

    @Test
    public void testGetAllMembers() {
        List<Member> members = memberService.getAllMembers();

        // 3개의 초기 데이터가 있을까?
        assertEquals(3, members.size());
    }

    @Test
    public void testGetMemberById_ExistingMember() {
        Member member = memberService.getMemberById(1L);

        assertNotNull(member);
        assertEquals(1L, member.getId());
        assertEquals("John Doe", member.getName());
        assertEquals("john@example.com", member.getEmail());
        assertEquals(30, member.getAge());
    }

    @Test
    public void testGetMemberById_NonExistingMember() {
        assertThrows(MemberNotFoundException.class, () -> {
            memberService.getMemberById(999L);
        });
    }

    @Test
    public void testCreateMember() {
        Member member = new Member("Test User", "test@example.com", 35);
        Member createdMember = memberService.createMember(member);

        assertNotNull(createdMember.getId());
        assertEquals("Test User", createdMember.getName());
        assertEquals("test@example.com", createdMember.getEmail());
        assertEquals(35, createdMember.getAge());

        // 저장된 화면을 조회해서 확인
        Member retrievedMember = memberService.getMemberById(createdMember.getId());
//        assertEquals(createdMember);
    }
}