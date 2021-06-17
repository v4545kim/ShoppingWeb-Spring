package controller.mall;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import bean.Member;
import bean.Order;
import controller.common.SuperClass;
import dao.CompositeDao;
import dao.MallDao;

@Controller
public class MallHistoryController extends SuperClass{
	private final String command = "/history.mall" ; 
	private ModelAndView mav = null ;
	//private String redirect = "redirect:/history.mall" ;
	
	@Autowired
	@Qualifier("malldao")
	private MallDao malldao ;
	
	public MallHistoryController() {
		super("ShopList", null);
		this.mav = new ModelAndView();
	}
	
	@GetMapping(command)
	public ModelAndView doGet(
			HttpSession session){
		System.out.println("히스토리 들어옴");
		
		Member loginfo = (Member)session.getAttribute("loginfo") ;
		
		if( loginfo == null ){  //미로그인 시
			//session.setAttribute("destination", "redirect:/Order.mall");
			this.mav.setViewName("redirect:/login.me");
		}else{ 
			//orderlists : 로그인 된 사람의 이전 쇼핑 내역을 저장하고 있는 컬렉션
//			List<Order> orderlists = new ArrayList<Order>() ;
			
			// lists : 현재 로그인 한 사람의 쇼핑 주문 내역들을 담고 있는 컬렉션(최근 주문 내역이 먼저 나옴)
			List<Order> lists = malldao.OrderMall(loginfo.getId()) ;
				
			this.mav.addObject("lists", lists);
			
			if(lists.size() == 0) {
				session.setAttribute("message", "이전 쇼핑 내역이 존재하지 않습니다.");	
			}			
			this.mav.setViewName(super.getpage);	
		}	
		return this.mav ;
	}
}