package sbd.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import sbd.example.domain.Member;
import sbd.example.service.MemberService;

import java.util.List;

@Controller
public class MemberController {
    private final MemberService memberService;

    @Autowired
    public MemberController( MemberService memberService){
        this.memberService = memberService;

    }
    @GetMapping("members/new")
    public String createForm(){
        System.out.println("1");
        return "members/createMemberForm";
    }

    @PostMapping("members/new")
    public String create(MemberForm form){
        System.out.println("2");
        Member member = new Member();
        member.setName(form.getName());

        memberService.join(member);
        System.out.println(member.getName());
        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model){
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }
}
