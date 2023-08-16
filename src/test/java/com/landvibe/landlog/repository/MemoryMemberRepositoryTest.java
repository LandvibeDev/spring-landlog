package com.landvibe.landlog.repository;

import com.landvibe.landlog.domain.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MemoryMemberRepositoryTest {
    MemoryMemberRepository repository = new MemoryMemberRepository();
    Member member = new Member("gildong", "abc@def.com", "1234");

    @BeforeEach
    void beforeEach() {
        repository.save(member);
    }

    @AfterEach
    void afterEach() {
        repository.clearStore();
    }

    @Test
    @DisplayName("유저 저장")
    void save() {
        //when
        Member result = repository.findById(member.getId()).get();

        //then
        assertThat(result).isEqualTo(member);
    }

    @Test
    @DisplayName("이름으로 유저 찾기")
    void findByName() {
        //when
        Member result = repository.findByName(member.getName()).get();

        //then
        assertThat(result).isEqualTo(member);
    }

    @Test
    @DisplayName("이메일로 유저 찾기")
    void findByEmail() {
        //when
        Member result = repository.findByEmail(member.getEmail()).get();

        //then
        assertThat(result).isEqualTo(member);
    }

    @Test
    @DisplayName("아이디로 유저 찾기")
    void findById() {
        //when
        Member result = repository.findById(member.getId()).get();

        //then
        assertThat(result).isEqualTo(member);
    }

    @Test
    @DisplayName("모든 유저 검색")
    void findAll() {
        //given
        Member anotherMember = new Member("gildong", "abc@def.com", "1234");
        repository.save(anotherMember);

        //when
        List<Member> result = repository.findAll();

        //then
        assertThat(result.size()).isEqualTo(2);
    }
}