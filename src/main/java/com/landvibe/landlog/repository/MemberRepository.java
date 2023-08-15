package com.landvibe.landlog.repository;

import com.landvibe.landlog.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    Member save(String name,  String email, String password);

    Optional<Member> findById(Long id);

    Optional<Member> findByName(String name);

    Optional<Member> findByEmail(String email);

    List<Member> findAll();
}
