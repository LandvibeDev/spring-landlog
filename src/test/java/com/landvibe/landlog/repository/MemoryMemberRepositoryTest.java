package com.landvibe.landlog.repository;

import com.landvibe.landlog.domain.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MemoryMemberRepositoryTest {
    MemoryMemberRepository repository = new MemoryMemberRepository();

    @AfterEach
    public void afterEach() {
        repository.clearStore();
    }

    @Test
    void save() {
        //given
        Member member = Member.builder().build();
        member.setName("spring");

        //when
        repository.save(member);

        //then
        Member result = repository.findById(member.getId()).get();
        assertThat(result).isEqualTo(member);
    }

    @Test
    public void findByName() {
        //given
        Member member1 = Member.builder().build();
        member1.setName("spring1");
        repository.save(member1);
        Member member2 = Member.builder().build();
        member2.setName("spring2");
        repository.save(member2);

        //when
        Member result = repository.findByName("spring1").get();

        //then
        assertThat(result).isEqualTo(member1);
    }

    @Test
    public void findAll() {
        //given
        Member member1 = Member.builder().build();
        member1.setName("spring1");
        repository.save(member1);
        Member member2 = Member.builder().build();
        member2.setName("spring2");
        repository.save(member2);

        //when
        List<Member> result = repository.findAll();

        //then
        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    void findByEmail() {
        //given
        Member member1 = Member.builder().build();
        member1.setEmail("spring1");
        repository.save(member1);
        Member member2 = Member.builder().build();
        member2.setEmail("spring2");
        repository.save(member2);

        //when
        Member result = repository.findByEmail("spring1").get();

        //then
        assertThat(result).isEqualTo(member1);
    }

}