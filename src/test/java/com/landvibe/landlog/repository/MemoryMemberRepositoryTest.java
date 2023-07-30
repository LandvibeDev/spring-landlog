package com.landvibe.landlog.repository;

import com.landvibe.landlog.domain.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class MemoryMemberRepositoryTest {

    MemoryMemberRepository repository = new MemoryMemberRepository();

    String name = "name";
    String email = "email";
    String password = "password";

    Member member = Member.builder()
            .name(name)
            .email(email)
            .password(password)
            .build();

    Long saveId = 1L;

    @BeforeEach
    public void afterEach() {
        repository.clear();
    }

    @Test
    void save() {
        //given

        //when
        repository.save(member);


        //then
        Member result = repository.findById(saveId).get();
        assertThat(result.getName()).isEqualTo(member.getName());
    }

    @Test
    public void findByName() {
        //given
        Member member1 = Member.builder()
                .name(name)
                .build();
        repository.save(member1);
        Member member2 = Member.builder()
                .name(name)
                .build();
        repository.save(member2);

        //when
        Member result = repository.findByName(name).get();

        //then
        assertThat(result.getName()).isEqualTo(member1.getName());
    }

    @Test
    public void findAll() {
        //given
        Member member1 = Member.builder()
                .name(name)
                .build();
        repository.save(member1);
        Member member2 = Member.builder()
                .name("spring2")
                .build();
        repository.save(member2);

        //when
        List<Member> result = repository.findAll();

        //then
        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    public void findByEmailTest() {

        Member member = Member.builder()
                .name(name)
                .email(email)
                .password(password)
                .build();
        repository.save(member);
        Optional<Member> optionalMember = repository.findByEmailAndPassword(email, password);
        Assertions.assertEquals(member.getName(), optionalMember.get().getName());
    }
}