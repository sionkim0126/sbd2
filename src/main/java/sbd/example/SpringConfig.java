package sbd.example;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sbd.example.repository.JdbcMemberRepository;
import sbd.example.repository.JdbcTemplateMemberRepository;
import sbd.example.repository.JpaMemberRepository;
import sbd.example.repository.MemberRepository;
import sbd.example.service.MemberService;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {

/*    private DataSource dataSource;
    @Autowired
    public SpringConfig(DataSource dataSource){
        this.dataSource = dataSource;
    }*/
    //jpa사용 EntityManager 사용시
    /*private EntityManager em;

    @Autowired
    public SpringConfig(EntityManager em){
        this.em = em;
    }*/
    //springData가 만든 구현체 등록
    private final MemberRepository memberRepository;

    @Autowired
    public SpringConfig(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }


    @Bean
    public MemberService memberService(){
        return new MemberService(memberRepository);
    }

///*    @Bean
//    public MemberRepository memberRepository(){
//     *//*   return new MemoryMemberRepository();*//*
//        *//*return new JdbcMemberRepository(dataSource);*//*
//        *//*return new JdbcTemplateMemberRepository(dataSource);*//*
//        *//*return new JpaMemberRepository(em);*//*
//    }*/
}
