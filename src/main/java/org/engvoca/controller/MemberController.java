package org.engvoca.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.engvoca.domain.MemberVO;
import org.engvoca.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MemberController 
{
	@Inject
	private MemberService service;
	
	@RequestMapping(value="/login",method=RequestMethod.GET)
    public String loginForm()
    {
		System.out.println("/login");
		
        return "login/loginForm";
    }
     
    @RequestMapping(value="/login",method=RequestMethod.POST)
    public String login(HttpSession session, MemberVO member, RedirectAttributes rttr) throws Exception
    {
        if (session.getAttribute("member") != null)
            session.removeAttribute("member"); // 既存値打ち除去
         
        MemberVO vo = service.login(member);
        if (vo != null)
        { 
            session.setAttribute("member", vo);
            System.out.println(vo.getId() + " 로그인 성공");
            
            return "redirect:/vocastudy/main";
        }
        
        System.out.println(member.getId() + " 로그인 실패");
        rttr.addFlashAttribute("msg", "아이디 또는 비밀번호가 일치하지 않습니다.");
        return "redirect:/login";
    }
 
    @RequestMapping(value="/logout")
    public String logout(HttpSession session) 
    {
        MemberVO vo = (MemberVO)session.getAttribute("member");
    	if (vo != null)
    		System.out.println(vo.getId() + " 로그아웃");
    	
        session.invalidate(); // session初期化
        return "redirect:/web/vocastudy/main";
    }
	
}
