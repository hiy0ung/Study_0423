package com.study.study2.service;

import com.study.study2.exception.MemberNotFoundException;
import com.study.study2.model.Member;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class MemberService {
    private final Map<Long, Member> memberStore = new ConcurrentHashMap<>();

    // auto-increment 비슷하게 구현하기 위해
    private final AtomicLong sequence = new AtomicLong(1);

    public MemberService() {
        createMember(new Member("John Doe", "john@example.com", 30));
        createMember(new Member("Jane Smith", "jane@example.com", 25));
        createMember(new Member("Bob Johnson", "bob@example.com", 40));
    }

    public List<Member> getAllMembers() {
        return new ArrayList<>(memberStore.values());
    }

    public Member getMemberById(Long id) {
        Member member = memberStore.get(id);
        if (member == null) {
            throw new MemberNotFoundException(id);
        }
        return member;
    }

    // 이메일 하나만 스트림을 써서 email, 첫번째
    // 없으면 MemberNotFoundException
    public Member getMemberByEmail(String email) {
        return memberStore.values().stream()
                .filter(member -> member.getEmail().equals(email))
                .findFirst()
                .orElseThrow(() -> new MemberNotFoundException(email));
    }

    public Member createMember(Member member) {
        // sequence 를 가지고 오는 동시에 1 증가
        Long id = sequence.getAndIncrement();
        member.setId(id);
        member.setCreatedAt(LocalDateTime.now());
        member.setUpdatedAt(LocalDateTime.now());
        memberStore.put(id, member);
        return member;
    }

    public Member updateMember(Long id, Member memberDetails) {
        Member existingMember = getMemberById(id);

        LocalDateTime oldUpdateAt = existingMember.getUpdatedAt();

        // 약간의 딜레이를 줘서 업데이트 시간이 다르게 설정되도록
        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {

        }

        existingMember.setName(memberDetails.getName());
        existingMember.setAge(memberDetails.getAge());
        existingMember.setEmail(memberDetails.getEmail());
        existingMember.setUpdatedAt(LocalDateTime.now());

        if (existingMember.getUpdatedAt().equals(oldUpdateAt)) {
            existingMember.setUpdatedAt(LocalDateTime.now().plusNanos(1000));
        }

        memberStore.put(id, existingMember);
        return existingMember;
    }
    public boolean deleteMember(Long id) {
        getMemberById(id);

        memberStore.remove(id);
        return true;
    }
}
