package com.landvibe.landlog.repository;

import com.landvibe.landlog.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MemoryMemberRepository implements MemberRepository {

    private static Map<Long, Member> memberStore = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        memberStore.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(memberStore.get(id));
    }

    @Override
    public Optional<Member> findByName(String name) {
        return memberStore.values().stream()
                .filter(member -> member.getName().equals(name))
                .findAny();
    }

    @Override
    public Optional<Member> findByEmail(String email) {
        return memberStore.values().stream()
                .filter(member -> member.getEmail().equals(email))
                .findAny();
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(memberStore.values());
    }

    @Override
    public Boolean noMember(){
        if(memberStore.isEmpty()) return true;
        else return false;
    }

    public void clearStore() {
        memberStore.clear();
    }
}
