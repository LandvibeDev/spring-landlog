package com.landvibe.landlog.repository;

import com.landvibe.landlog.domain.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

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
        Member member = new Member();
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
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);
        Member member2 = new Member();
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

<<<<<<< HEAD
    private Member createMember() {
=======
    @Test
    public void findByEmailAndPassword(){
        //given
>>>>>>> 732cb05 (add test code)
        Member member1 = new Member();
        member1.setName("name1");
        member1.setEmail("email1");
        member1.setPassword("password1");
        repository.save(member1);
<<<<<<< HEAD
        return member1;
    }

    @Test
    @DisplayName("이메일과 비밀번호로 회원 조회 성공 테스트")
    public void findByEmailAndPassword_success(){
        //given
        Member member1 = createMember();

        //when
        Optional<Member> successResult = repository.findByEmailAndPassword("email1", "password1");

        //then
        assertThat(successResult.get()).isEqualTo(member1);
    }

    @Test
    @DisplayName("이메일과 비밀번호로 회원 조회 실패 테스트")
    public void findByEmailAndPassword_fail(){
        //given
        Member member1 = createMember();

        //when
        Optional<Member> failedResult = repository.findByEmailAndPassword("email2", "password2");

        //then
=======

        //when
        Optional<Member> successResult = repository.findByEmailAndPassword("email1", "password1");
        Optional<Member> failedResult = repository.findByEmailAndPassword("email2", "password2");

        //then
        assertThat(successResult).isPresent();
        assertThat(successResult.get()).isEqualTo(member1);

>>>>>>> 732cb05 (add test code)
        assertThat(failedResult).isEmpty();
    }
}