package sbd.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sbd.example.repository.MemberRepository;
import sbd.example.repository.MemoryMemberRepository;
import sbd.example.service.MemberService;

@Configuration
public class SpringConfig {

    @Bean
    public MemberService memberService(){
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository(){
        return new MemoryMemberRepository();
    }
}
