package com.landvibe.landlog.repository;

import com.landvibe.landlog.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MemoryMemberRepositoryTest {

    MemoryMemberRepository repository;

    String name = "a";
    String email = "a@spring.com";
    String password = "1234";
    Member member = new Member(name, email, password);

    @BeforeEach
    public void beforeEach() {
        repository = new MemoryMemberRepository();
        repository.save(member);
    }

    @AfterEach
    public void afterEach() {
        repository.clearStore();
    }

    @Test
    public void findByName() {
        //when
        Member result = repository.findByName(name).get();

        //then
        assertThat(member).isEqualTo(result);
    }

    @Test
    public void findByEmail() {
        //when
        Member result = repository.findByEmail(member.getEmail()).get();

        //then
        assertThat(member).isEqualTo(result);
    }


    @Test
    public void findAll() {
        //given
        int expectedSize = 1;

        //when
        int resultSize = repository.findAll().size();

        //then
        assertThat(resultSize).isEqualTo(expectedSize);
    }

    @Test
    public void findById() {
        //when
        Member result = repository.findById(member.getId()).get();

        //then
        Assertions.assertThat(result).isEqualTo(member);
    }
}