package controller.mall;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controller.common.SuperClass;
import dao.CompositeDao;
import shopping.MyCartList;

@Controller
public class MallDeleteController extends SuperClass{
	private final String command = "/delete.mall" ; 
	private ModelAndView mav = null ;
	//private String redirect = "redirect:/delete.mall" ;
	
	@Autowired
	@Qualifier("cdao")
	private CompositeDao dao ;
	
	public MallDeleteController() {
		super(null, null);
		this.mav = new ModelAndView();
	}
	
	@GetMapping(command)
	public ModelAndView doGet(
			@RequestParam(value = "pnum", required = true) int pnum,
			HttpSession session){
		if (session.getAttribute("loginfo") == null) {
			this.mav.setViewName("redirect:/login.me");
		} else {
			MyCartList mycart = (MyCartList)session.getAttribute("mycart") ;
			if (mycart == null) {
				mycart = new MyCartList() ;
			}
			mycart.DeleteOrder(pnum); 
			session.setAttribute("mycart", mycart); 
			this.mav.setViewName("redirect:/list.mall");
		}
		return this.mav ;
	}
}