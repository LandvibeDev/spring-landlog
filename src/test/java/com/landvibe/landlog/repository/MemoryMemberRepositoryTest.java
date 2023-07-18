package com.landvibe.landlog.repository;

import com.landvibe.landlog.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class MemoryMemberRepositoryTest {

    MemoryMemberRepository repository;

    //member1
    String name1 = "spring1";
    String email1 = "spring1@spring.com";
    String password1 = "1234";

    //member2
    String name2 = "spring2";
    String email2 = "spring2@spring.com";
    String password2 = "1234";

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
        Member member = new Member(name1, email1, password1);

        //when
        repository.save(member);

        //then
        Member result = repository.findById(member.getId()).get();
        assertThat(result).isEqualTo(member);
    }

    @Test
    public void findByName() {
        //given
        Member member1 = new Member(name1, email1, password1);
        repository.save(member1);
        Member member2 = new Member(name2, email2, password2);
        repository.save(member2);

        //when
        Member result = repository.findByName("spring1").get();

        //then
        assertThat(result).isEqualTo(member1);
    }

    @Test
    public void findByEmail() {
        //given
        Member member1 = new Member(name1, email1, password1);
        repository.save(member1);
        Member member2 = new Member(name2, email2, password2);
        repository.save(member2);

        //when
        Member result = repository.findByEmail("spring1@spring.com").get();

        //then
        assertThat(result).isEqualTo(member1);
    }


    @Test
    public void findAll() {
        //given
        Member member1 = new Member(name1, email1, password1);
        repository.save(member1);
        Member member2 = new Member(name2, email2, password2);
        repository.save(member2);

        //when
        List<Member> result = repository.findAll();

        //then
        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    public void findById() {
        //given
        Member member1 = new Member(name1, email1, password1);
        repository.save(member1);

        //when
        Optional<Member> actual = repository.findById(member1.getId());

        //then
        Assertions.assertThat(actual.get()).isEqualTo(member1);
    }
}