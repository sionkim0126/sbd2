package sbd.example.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import sbd.example.domain.Member;
import sbd.example.repository.MemberRepository;
import sbd.example.repository.MemoryMemberRepository;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional //테스트가 끝난 후 자동으로 롤백하여 데이터베이스에 변경 사항이 반영되지 않게 막아줍니다.
class MemberServiceIntegrationTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;



    @Test
    void join() {
        //given 주어진 상황
        Member member = new Member();
        member.setName("sion4");

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
        member1.setName("sion4");

        Member member2 = new Member();
        member2.setName("sion4");

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
}