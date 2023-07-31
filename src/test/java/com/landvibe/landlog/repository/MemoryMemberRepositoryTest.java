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
        Member member = Member.builder()
                .name("spring")
                .build();

        //when
        repository.save(member);

        //then
        Member result = repository.findById(member.getId()).get();
        assertThat(result).isEqualTo(member);
    }

    @Test
    public void findByName() {
        //given
        Member member1 = Member.builder()
                .name("spring1")
                .build();

        Member member2 = Member.builder()
                .name("spring2")
                .build();

        repository.save(member1);
        repository.save(member2);

        //when
        Member result = repository.findByName("spring1").get();

        //then
        assertThat(result).isEqualTo(member1);
    }

    @Test
    public void findAll() {
        //given
        Member member1 = Member.builder()
                .name("spring1")
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
    @DisplayName("이메일과 비밀번호로 조회 실패")
    public void findMemberByEmailAndPassword_whenNoSuchMemberByEmailAndPassword_returnNull() {
        //given
        Member member1 = Member.builder().build();
        createNewMember(member1);
        repository.save(member1);
        Member failureMember = Member.builder()
                .id(1L)
                .name("bbN")
                .email("bbI")
                .password("bbP")
                .build();

        //when
        Optional<Member> failByEmailAndPassword = repository.findByEmailAndPassword(failureMember.getEmail(), failureMember.getPassword());

        //then
        assertThat(failByEmailAndPassword).isEmpty();
    }

    private static void createNewMember(Member member1) {
        member1.setName("aaN");
        member1.setEmail("aaI");
        member1.setPassword("aaP");
    }

    @Test
    @DisplayName("이메일과 비밀번호로 조회 성공")
    public void findMemberByEmailAndPassword_whenFindSuchMemberByEmailAndPassword_returnTrue() {
        //given
        Member member1 = Member.builder().build();
        createNewMember(member1);
        repository.save(member1);
        Member successMember = Member.builder()
                .id(1L)
                .name("aaN")
                .email("aaI")
                .password("aaP")
                .build();

        //when
        Optional<Member> successByEmailAndPassword = repository.findByEmailAndPassword(successMember.getEmail(), successMember.getPassword());

        //then
        assertThat(successByEmailAndPassword.get()).isEqualTo(member1);
    }
}
