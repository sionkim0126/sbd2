package sbd.example.service;

import sbd.example.domain.Member;
import sbd.example.repository.MemberRepository;
import sbd.example.repository.MemoryMemberRepository;

import java.util.List;
import java.util.Optional;

public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }

    /*회원가입*/
    public long join(Member member){
        //동명이인 중복회원 x
        //1. Optional로 반환해서 먼저 꺼내는 건 추천하지 않는다
        /*Optional<Member> result = memberRepository.findByName(member.getName());
        result.ifPresent(m -> {
            throw new IllegalStateException("이미 존재하는 회원입니다");
        });*/

        //2. 찾는 동시에 중복이 있다면 메세지 전송 코드 갈략해짐
        //Ctrl + Art + M = 메서드 만들기
        validateDuplicateMember(member);//중복 회원 검증

        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                        .ifPresent(m -> {
                            throw new IllegalStateException("이미 존재하는 회원입니다");
                        });
    }

    //전체 회원 조회
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(long memberId){
        return memberRepository.findById(memberId);
    }
}
