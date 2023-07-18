package com.landvibe.landlog.repository;

import com.landvibe.landlog.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MemoryMemberRepositoryTest {

    MemoryMemberRepository repository;

    String name = "a";
    String email = "a@spring.com";
    String password = "1234";

    String secondName = "b";
    String secondEmail = "b@spring.com";
    String secondPassword = "1234";

    @BeforeEach
    public void beforeEach() {
        repository = new MemoryMemberRepository();
    }

    @AfterEach
    public void afterEach() {
        repository.clearStore();
    }

    @Test
    void save() {
        //given
        Member member = new Member(name, email, password);

        //when
        repository.save(member);

        //then
        Member result = repository.findById(member.getId()).get();
        assertThat(result).isEqualTo(member);
    }

    @Test
    public void findByName() {
        //given
        Member member = new Member(name, email, password);
        repository.save(member);

        Member secondMember = new Member(secondName, secondEmail, secondPassword);
        repository.save(secondMember);

        //when
        Member result = repository.findByName(member.getName()).get();

        //then
        assertThat(member).isEqualTo(result);
    }

    @Test
    public void findByEmail() {
        //given
        Member member = new Member(name, email, password);
        repository.save(member);

        Member secondMember = new Member(secondName, secondEmail, secondPassword);
        repository.save(secondMember);

        //when
        Member result = repository.findByEmail(member.getEmail()).get();

        //then
        assertThat(member).isEqualTo(result);
    }


    @Test
    public void findAll() {
        //given
        Member member = new Member(name, email, password);
        repository.save(member);

        Member secondMember = new Member(secondName, secondEmail, secondPassword);
        repository.save(secondMember);

        int expectedSize = 2;

        //when
        List<Member> result = repository.findAll();

        //then
        assertThat(result.size()).isEqualTo(expectedSize);
    }

    @Test
    public void findById() {
        //given
        Member member = new Member(name, email, password);
        repository.save(member);

        //when
        Member result = repository.findById(member.getId()).get();

        //then
        Assertions.assertThat(result).isEqualTo(member);
    }
}