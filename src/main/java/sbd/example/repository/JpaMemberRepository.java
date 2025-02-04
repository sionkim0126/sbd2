package sbd.example.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import sbd.example.domain.Member;

import java.util.List;
import java.util.Optional;


//Art + Enter = 인터페이스 메서드 구현
public class JpaMemberRepository implements MemberRepository{

    private final EntityManager em;

    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }


    @Override
    public Member save(Member member) {
        em.persist(member);
        return member;
    }

    @Override
    public Optional<Member> findById(long id) {
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
    }

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        // 네이티브 SQL을 사용하여 모든 Member 조회
        return em.createQuery("select m from  Member m", Member.class)  // 결과를 Member 엔티티로 매핑
        .getResultList();
    }
}
