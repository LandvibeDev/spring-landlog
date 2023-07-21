package com.landvibe.landlog.repository;

import com.landvibe.landlog.domain.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class MemoryMemberRepositoryTest {

    MemoryMemberRepository repository = new MemoryMemberRepository();

    String name = "name";
    String email = "email";
    String password = "password";

    @AfterEach
    public void afterEach() {
        repository.clear();
    }

    @Test
    void save() {
        //given
        Member member = new Member();
        member.setName(name);

        //when
        repository.save(member);

        //then
        Member result = repository.findById(member.getId()).get();
        assertThat(result).isEqualTo(member);
    }

    @Test
    public void findByName() {
        //given
        Member member1 = new Member();
        member1.setName(name);
        repository.save(member1);
        Member member2 = new Member();
        member2.setName(name);
        repository.save(member2);

        //when
        Member result = repository.findByName(name).get();

        //then
        assertThat(result).isEqualTo(member1);
    }

    @Test
    public void findAll() {
        //given
        Member member1 = new Member();
        member1.setName(name);
        repository.save(member1);
        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        //when
        List<Member> result = repository.findAll();

        //then
        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    public void findByEmailTest() {

        Member member = new Member();
        member.setEmail(email);
        member.setPassword(password);
        member.setName(name);
        repository.save(member);
        Optional<Member> optionalMember = repository.findByEmailAndPassword(email, password);
        Assertions.assertEquals(member, optionalMember.get());
    }
}