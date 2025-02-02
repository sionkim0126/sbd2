package sbd.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sbd.example.repository.JdbcMemberRepository;
import sbd.example.repository.JdbcTemplateMemberRepository;
import sbd.example.repository.MemberRepository;
import sbd.example.service.MemberService;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {

    private DataSource dataSource;
    @Autowired
    public SpringConfig(DataSource dataSource){
        this.dataSource = dataSource;
    }

    @Bean
    public MemberService memberService(){
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository(){
     /*   return new MemoryMemberRepository();*/
        /*return new JdbcMemberRepository(dataSource);*/
        return new JdbcTemplateMemberRepository(dataSource);
    }
}
