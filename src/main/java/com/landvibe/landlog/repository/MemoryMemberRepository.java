package com.landvibe.landlog.repository;

import com.landvibe.landlog.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MemoryMemberRepository implements MemberRepository {

    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public Member save(Member member) {
        String name = member.getName();
        String email = member.getEmail();
        String password = member.getPassword();
        Member newMember = Member.builder()
                .name(name)
                .email(email)
                .password(password)
                .id(++sequence)
                .build();
        store.put(sequence, newMember);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream()
                .filter(member -> member.getName().equals(name))
                .findAny();
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }


    @Override
    public Optional<Member> findByEmailAndPassword(String email, String password) {
        return store.values().stream()
                .filter(member -> member.getEmail().equals(email) && member.getPassword().equals(password))
                .findAny();
    }

    @Override
    public void clear() {
        store.clear();
    }

}
