package controller.mall;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import bean.Member;
import controller.common.SuperClass;
import shopping.MyCartList;

@Controller
public class MallDeleteController extends SuperClass{
	private final String command = "/delete.mall";
	private ModelAndView mav = null;
	
	public MallDeleteController() {
		this.mav = new ModelAndView();
	}
	
	@GetMapping(command)
	public ModelAndView doGet(
			@RequestParam(value="pnum", required = true) int pnum,
			HttpSession session) {
		
		Member loginfo = (Member)session.getAttribute("loginfo");
		
		if(loginfo == null) {
			this.mav.setViewName("redirect:/login.me");
		} else {
			MyCartList mycart = (MyCartList)session.getAttribute("mycart");
			
			if(mycart == null) {
				mycart = new MyCartList();
			}
			mycart.DeleteOrder(pnum);
			
			if (mycart.GetAllOrderLists().size() == 0) {
				// 구매 목록이 없으면, 상품 목록 페이지로 이동
				String message = "구매 목록이 존재하지 않습니다.";
				session.setAttribute("errmsg", message);
				
				
				this.mav.setViewName("redirect:/list.pr");
			} else {
				// 구매 목록이 있으면, 장바구니 목록 페이지로 이동
				this.mav.setViewName("redirect:/list.mall");
			}
			
			session.setAttribute("mycart", mycart);
		}
		return this.mav;
	}
}