package com.landvibe.landlog.repository;

import com.landvibe.landlog.controller.MemberForm;
import com.landvibe.landlog.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class MemoryMemberRepositoryTest {

    MemoryMemberRepository repository = new MemoryMemberRepository();
    MemberForm memberForm1 = new MemberForm("spring1","spring1@spring.com","1234");
    MemberForm memberForm2 = new MemberForm("spring2","spring2@spring.com","1234");

    @AfterEach
    public void afterEach() {
        repository.clearStore();
    }

    @Test
    void save() {
        //given
        Member member = new Member(memberForm1);

        //when
        repository.save(member);

        //then
        Member result = repository.findById(member.getId()).get();
        assertThat(result).isEqualTo(member);
    }

    @Test
    public void findByName() {
        //given
        Member member1 = new Member(memberForm1);
        repository.save(member1);
        Member member2 = new Member(memberForm2);
        repository.save(member2);

        //when
        Member result = repository.findByName("spring1").get();

        //then
        assertThat(result).isEqualTo(member1);
    }

    @Test
    public void findByEmail() {
        //given
        Member member1 = new Member(memberForm1);
        repository.save(member1);
        Member member2 = new Member(memberForm2);
        repository.save(member2);

        //when
        Member result = repository.findByEmail("spring1@spring.com").get();

        //then
        assertThat(result).isEqualTo(member1);
    }



    @Test
    public void findAll() {
        //given
        Member member1 = new Member(memberForm1);
        repository.save(member1);
        Member member2 = new Member(memberForm2);
        repository.save(member2);

        //when
        List<Member> result = repository.findAll();

        //then
        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    public void findById(){
        //given
        Member member1 = new Member(memberForm1);
        repository.save(member1);

        //when
        Optional<Member> actual = repository.findById(member1.getId());

        //then
        Assertions.assertThat(actual.get()).isEqualTo(member1);
    }
}