package com.study.study3.library.repository;

import com.study.study3.library.model.Member;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class MemberRepository {
    // 로거
    // 동시성 이슈를 고려한 ConcurrentHashMap 으로 구성한 loanStore
    // id 생성을 위한 AtomicLong 사용
    // emailIndex -> isbn 고유하다
    private static final Logger logger = LoggerFactory.getLogger(MemberRepository.class);
    private final Map<Long, Member> memberStore = new ConcurrentHashMap<>();
    private final Map<String, Long> emailIndex = new ConcurrentHashMap<>();
    private final AtomicLong sequence = new AtomicLong(1);

    /**
     * 모든 회원 조회
     */
    public List<Member> findAll() {
        return new ArrayList<>(memberStore.values());
    }

    /**
     * ID 로 회원 조회
     */
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(memberStore.get(id));
    }

    /**
     * Email 로 회원 조회 (깃헙 코드 확인)
     */
    public Optional<Member> findByEmail(String email) {
        return memberStore.values().stream()
                .filter(member -> member.getEmail().equals(email))
                .findFirst();
    }

    /**
     * keyword 로 회원 검색 (리스트)
     */
    public List<Member> findByKeyword(String keyword) {
        logger.debug("Finding books by keyword: {}", keyword);

        if (keyword == null || keyword.trim().isEmpty()) {
            return new ArrayList<>();
        }
        String lowerKeyword = keyword.toLowerCase();

        return memberStore.values().stream()
                .filter(member ->
                        member.getName().toLowerCase().contains(lowerKeyword) ||
                        member.getEmail().toLowerCase().contains(lowerKeyword) ||
                        member.getPhoneNumber().toLowerCase().contains(lowerKeyword))
                .toList();
    }

    /**
     * 회원 저장(생성/수정)
     */
    public Member save(Member member) {
        if (member.getId() == null) {
            Long id = sequence.getAndIncrement();
            member.setId(id);

            if (member.getCreatedAt() == null) {
                member.setCreatedAt(LocalDateTime.now());
            }

            logger.debug("Creating new member with id: {}", id);
        }
        logger.debug("Updating member with id: {}", member.getId());
        memberStore.put(member.getId(), member);

        return member;
    }

    /**
     * 회원 삭제
     */
    public boolean delete(Long id) {
        return memberStore.remove(id) != null;
    }
}
