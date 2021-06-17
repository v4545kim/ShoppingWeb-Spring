package controller.mall;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import controller.common.SuperClass;
import dao.CompositeDao;


public class Template01Controller extends SuperClass{
	private final String command = "/exam01.mall" ; 
	private ModelAndView mav = null ;
	private String redirect = "redirect:/exam01.mall" ;
	
	@Autowired
	@Qualifier("cdao")
	private CompositeDao dao ;
	
	public Template01Controller() {
		super("View01", "View01");
		this.mav = new ModelAndView();
	}
	@PostMapping(command)
	public ModelAndView doPost(){
		this.mav.setViewName(super.postpage);
		return this.mav ;
	}
}