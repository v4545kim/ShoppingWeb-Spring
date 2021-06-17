package controller.view;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import bean.Combo02;
import controller.common.SuperClass;
import dao.CompositeDao;

@Controller
public class View02Controller extends SuperClass{
	private final String command = "/exam02.vw" ; 
	private ModelAndView mav = null ;
	private String redirect = "redirect:/exam02.vw" ;
	
	@Autowired
	@Qualifier("cdao")
	private CompositeDao dao ;
	
	public View02Controller() {
		super("View02", "View02");
		this.mav = new ModelAndView();
	}
	
	@GetMapping(command)
	public ModelAndView doGet(){
		List<Combo02> lists = dao.View02() ;
		this.mav.addObject("lists", lists);
		this.mav.setViewName(super.getpage);
		return this.mav ;
	}
}