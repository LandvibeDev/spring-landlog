package com.landvibe.landlog.repository;

import com.landvibe.landlog.domain.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MemoryMemberRepositoryTest {

    @Autowired
    MemberRepository repository;

    @BeforeEach
    void clear() {
        repository.clear();
    }

    @Test
    void save() {
        //given
        Member member = new Member();
        member.setName("spring");

        //when
        repository.save(member);

        //then
        Member result = repository.findById(member.getId()).get();
        assertThat(result.getId()).isEqualTo(member.getId());
    }

    @Test
    public void findByName() {
        //given
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        //when
        Member result = repository.findByName("spring1").get();

        //then
        assertThat(result.getName()).isEqualTo(member1.getName());
    }

    @Test
    public void findByEmail() {
        //given
        Member member = new Member();
        member.setEmail("123@naver.com");
        repository.save(member);

        //when
        Member result = repository.findByEmail("123@naver.com").get();

        //then
        assertThat(result.getEmail()).isEqualTo(member.getEmail());
    }

    @Test
    public void findAll() {
        //given
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);
        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        //when
        List<Member> result = repository.findAll();

        //then
        assertThat(result.size()).isEqualTo(2);
    }
}