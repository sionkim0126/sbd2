package sbd.example.repository;

import sbd.example.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Member save(Member member);
    Optional<Member> findById(long id); //없을때 null로 반환하는 명령어
    Optional<Member> findByName(String name);
    List<Member> findAll();
}
