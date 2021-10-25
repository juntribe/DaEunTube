package com.daeuntube.controller;

import com.daeuntube.config.SessionConstants;
import com.daeuntube.dto.LoginFormDTO;
import com.daeuntube.dto.MemberDTO;
import com.daeuntube.entity.Member;
import com.daeuntube.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RequiredArgsConstructor
@Controller
@Log4j2
public class MemberController {

    private final MemberService memberService;


    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("memberDTO", new MemberDTO());
        return "member/registerForm";
    }

    /*
     * 회원 가입
     */
    
    @PostMapping("/register")
    public String registerMember(@Valid MemberDTO memberDTO, BindingResult bindingResult,Model model){

        if(bindingResult.hasErrors()){
            return "member/registerForm";
        }
        try{
            memberService.joinUser(memberDTO);
        // 중복 확인
        }catch (IllegalStateException e){
            model.addAttribute("errorMessage", e.getMessage());
            return "member/registerForm";
        }
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginForm(@ModelAttribute LoginFormDTO loginFormDTO){
        return "member/loginForm";
    }

    /*
    * 로그인
    */
    @PostMapping("/login")
    public String login(@ModelAttribute @Validated LoginFormDTO loginFormDTO,
                        BindingResult bindingResult,@RequestParam(defaultValue = "/") String redirectURL,
                         HttpServletRequest request){

        if (bindingResult.hasErrors()){
            return "member/loginForm";
        }
        Member member = memberService.LoginUser(loginFormDTO.getLoginId(), loginFormDTO.getPassword());
        // 유효성 실패시 메시지 전달
        if (member == null){
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다");
            return "member/loginForm";
        }
        HttpSession session = request.getSession();
        session.setAttribute(SessionConstants.LOGIN_MEMBER,member);

        return "redirect:"+ redirectURL;
    }
    @PostMapping("/logout")
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession(false );
        if (session !=null){
            session.invalidate();
        }
        return "redirect:/";
    }



}
