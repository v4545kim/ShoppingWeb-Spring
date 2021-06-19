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
import dao.MallDao;

@Controller
public class MallHistoryController extends SuperClass{
	private final String command = "/history.mall";
	private ModelAndView mav = null;
	private final String redirect = "redirect:/수정02.mall";
	
	@Autowired
	@Qualifier("malldao")
	private MallDao malldao ;
	
	public MallHistoryController() {
		super("ShopList", null);
		this.mav = new ModelAndView();
	}
	
	@GetMapping(command)
	public ModelAndView doGet(
			HttpSession session) {
		System.out.println("나의 쇼핑 내역 보기");
		
		Member loginfo = (Member)session.getAttribute("loginfo");
		
		if (loginfo == null) {
			this.mav.setViewName("redirect:/login.me");
			
		} else {
			// lists는 로그인 한 사람의 과거 쇼핑 내역 정보를 저장하고 있는 컬렉션
			//	최근 주문 내역을 먼저 보여 줍니다.
			List<Order> lists =  malldao.OrderMall(loginfo.getId());
			
			if (lists.size() == 0) { // 과거 구매 내역 없음
					session.setAttribute("errmsg", "이전 쇼핑 내역이 존재하지 않습니다.");
					
					this.mav.setViewName("redirect:/main.co");
			} else { // 과거 구매 내역 있음
				this.mav.addObject("lists", lists);
				
				this.mav.setViewName(super.getpage);
			}
		}
		return this.mav;
	}
}


