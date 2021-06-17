package controller.member;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import bean.CheckBean;
import bean.Member;
import controller.common.SuperClass;
import dao.MemberDao;

@Controller
public class MemberInsertController extends SuperClass{
	private final String command = "/insert.me";
	private ModelAndView mav = null;
	private final String redirect = "redirect:/login.me";
	
	@Autowired
	@Qualifier("mdao")
	private MemberDao mdao ;
	
	@ModelAttribute("member")
	public Member mymember() {
		return new Member();
	}
	
	@ModelAttribute("checklist")
	public List<CheckBean> aaa(){
		List<CheckBean> lists = this.mdao.GetList("member", "hobby", "checkbox");
		return lists;
	}
	
	@ModelAttribute("radiolist")
	public List<CheckBean> bbb(){
		List<CheckBean> lists = this.mdao.GetList("member", "gender", "radio");
		return lists;
	}
	
	@ModelAttribute("selectlist")
	public List<CheckBean> ccc(){
		List<CheckBean> lists = this.mdao.GetList("member", "job", "select");
		return lists;
	}
	
	public MemberInsertController() {
		super("meInsertForm", "boList");
		this.mav = new ModelAndView();
	}
	
	@GetMapping(command)
	public ModelAndView doGet() {
		this.mav.setViewName(super.getpage);
		return this.mav;
	}
	
	@PostMapping(command)
	public ModelAndView doPost(
			@ModelAttribute("member") @Valid Member xxx, BindingResult asdf) {
		// 커맨드 객체를 사용하여 유효성 검사를 진행하여아 합니다.
		if (asdf.hasErrors()) {
			System.out.println("유효성 검사에 문제가 있음");
			System.out.println(xxx.toString());
			System.out.println(asdf.toString());
			mav.addObject("bean",xxx);
			mav.setViewName(super.getpage);
			
		} else {
			System.out.println("유효성 검사에 문제가 없음");
			int cnt = -1;
			cnt = mdao.InsertData(xxx);
			
			mav.setViewName(command);
			mav.setViewName(redirect);
		}
		
		
		return this.mav;
	}
}