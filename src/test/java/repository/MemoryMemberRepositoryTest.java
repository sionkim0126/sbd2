package repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import sbd.example.domain.Member;
import sbd.example.repository.MemberRepository;
import sbd.example.repository.MemoryMemberRepository;
import java.util.List;
import java.util.Optional;

class MemoryMemberRepositoryTest {

    MemoryMemberRepository repository = new MemoryMemberRepository();

     @AfterEach
     public void afeterEach(){
        repository.clearStore();
    }

     @Test
    public void save(){
         Member member = new Member();
         member.setName("spring");

         repository.save(member);

         Member result = repository.findById(member.getId()).get();
         /*Assertions.assertEquals(member, result); // jupiter사용하는 방법*/
         Assertions.assertThat(member).isEqualTo(result); //다른 방법 좀 더 편하게 사용
     }

     @Test
    public void findByName(){
         Member member1 = new Member();
         member1.setName("spring1");
         repository.save(member1);

         Member member2 = new Member();
         member2.setName("spring2");
         repository.save(member2);

         Member result = repository.findByName("spring1").get();

         Assertions.assertThat(member1).isEqualTo(result);
     }

     @Test
    public void findAll() {
         Member member1 = new Member();
         member1.setName("spring1");
         repository.save(member1);

         Member member2 = new Member();
         member2.setName("spring2");
         repository.save(member2);

         List<Member> result = repository.findAll();

         Assertions.assertThat(result.size()).isEqualTo(2);
     }
}
