package com.example.chapter6.controller;

import com.example.chapter6.dto.MemberForm;
import com.example.chapter6.entity.Member;
import com.example.chapter6.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
public class MemberController {
    @Autowired
    private MemberRepository memberRepository;

    @GetMapping("/signup")
    public String newMemberForm() {
        return "members/new";
    }

    @PostMapping("/join")
    public String createMember(MemberForm memberForm) {
        log.info(memberForm.toString());
        // System.out.println(memberForm.toString());

        Member memberEntity = memberForm.toEntity(); // dto를 entity로 변경
        log.info(memberEntity.toString());
        // System.out.println(memberEntity.toString());

        Member saved = memberRepository.save(memberEntity);
        log.info(saved.toString());
        // System.out.println(saved.toString());
        return "redirect:/members/" + saved.getId(); // repository에 저장될때 id 결정
    }

    @GetMapping("members/{id}")
    public String show(@PathVariable Long id, Model model) {
        log.info("id =  " + id);
        // repository에서 id값에 맞는 값 찾기
        Member memberEntity = memberRepository.findById(id).orElse(null);
        
        // member라는 이름으로 memberEntity 등록 {{#member}} {{/member}}로 지정 구역에서 사용 가능
        model.addAttribute("member", memberEntity); 
        return "members/show";
    }

    @GetMapping("/members")
    public String index(Model model){
        // 데이터 목록 가져오기
        Iterable<Member> memberEntityList = memberRepository.findAll();

        // 모델에 데이터 등록
        model.addAttribute("memberList", memberEntityList);

        return "members/index";
    }

    @GetMapping("/members/{id}/edit")
    public String edit(@PathVariable Long id, Model model){
        Member memberEntity = memberRepository.findById(id).orElse(null);
        model.addAttribute("member", memberEntity);
        return "members/edit";
    }

    @PostMapping("/members/update")
    public String update(MemberForm memberForm){
        log.info(memberForm.toString());
        Member memberEntity = memberForm.toEntity();
        log.info(memberEntity.toString());
        Member target = memberRepository.findById(memberEntity.getId()).orElse(null);
        if(target != null){
            memberRepository.save(memberEntity);
        }

        return "redirect:/members/" + memberEntity.getId();
    }

    @GetMapping("/members/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rttr){
        log.info("삭제 요청이 들어왔습니다!");
        Member target = memberRepository.findById(id).orElse(null);
        log.info(target.toString());
        if(target != null){
            memberRepository.delete(target);
            rttr.addFlashAttribute("msg", "삭제됐습니다.");
        }
        return "redirect:/members";
    }
}
