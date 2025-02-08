package sbd.example.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sbd.example.domain.Member;
import sbd.example.repository.MemberRepository;
import sbd.example.repository.MemoryMemberRepository;

import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    MemberService memberService;
    MemoryMemberRepository memoryMemberRepository;

    @BeforeEach
    public void beforEach(){
        memoryMemberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memoryMemberRepository);
    }

    @AfterEach
    public void afterEach(){
        memoryMemberRepository.clearStore();
    }

    @Test
    void join() {
        //given 주어진 상황
        Member member = new Member();
        member.setName("sion");

        //when 실행했을 때
        long saveInfo = memberService.join(member);

        //then 결과는 이렇게 나와야한다
        Member findMenber = memberService.findOne(saveInfo).get();
        Assertions.assertThat(member.getName()).isEqualTo(findMenber.getName());
    }

    @Test
    public void 중복회원검증(){
        //given 주어진 상황
        Member member1 = new Member();
        member1.setName("sion");

        Member member2 = new Member();
        member2.setName("sion");

        //when 이렇게 했을 때
        memberService.join(member1);
        //1.try catch로 예외 잡을 때
/*        try{
            memberService.join(member2);
        }catch (IllegalStateException e){
            //then 이렇게 나와야한다
            Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다");
        }*/
        //2.assert를 이용
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다");
    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }



}