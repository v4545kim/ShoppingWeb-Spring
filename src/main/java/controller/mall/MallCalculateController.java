package controller.mall;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import bean.Member;
import controller.common.SuperClass;
import dao.MallDao;
import shopping.MyCartList;

@Controller
public class MallCalculateController extends SuperClass{
	private final String command = "/calculate.mall";
	private ModelAndView mav = null;
	private final String redirect = "redirect:/history.mall";
	
	@Autowired
	@Qualifier("malldao")
	private MallDao malldao ;
	
	public MallCalculateController() {
		super(null, null);
		this.mav = new ModelAndView();
	}
	
	@GetMapping(command)
	public ModelAndView doGet(
			HttpSession session) {
		System.out.println("장바구니 내역을 사용하여 계산을 수행합니다.");
		
		// loginfo는 로그인한 사람으로 계산을 수행하는 주체
		Member loginfo = (Member)session.getAttribute("loginfo");
		
		if (loginfo == null) {
			this.mav.setViewName("redirect:/login.me");
			
		} else {
			MyCartList mycart = (MyCartList)session.getAttribute("mycart");
			
			if(mycart != null) {
				Map<Integer, Integer> maplists = mycart.GetAllOrderLists();
				int totalPoint = (Integer)session.getAttribute( "totalPoint" );
				
				// Caclulate() 메소드는 회원 loginfo에 대하여, 장바구니 maplists를 계산합니다.
				//	그리고 적립 포인트 totalPoint를 사용자에게 적립해 줍니다.
				malldao.Calculate(loginfo, maplists, totalPoint);
				
				// 게산이 끝나고, 세션 영역의 모든 정보를 클리어 해주어야 합니다.
				session.removeAttribute("shoplists"); // 쇼핑 정보 삭제
				session.removeAttribute("totalAmount"); // 금액 정보 삭제
				session.removeAttribute("totalPoint"); // 포인트 정보 삭제
				session.removeAttribute("mycart"); // 카트 반납하기
				session.setAttribute("message", "결제를 완료했습니다.");
				
				this.mav.setViewName(redirect);
			}
		}
		return this.mav;
	}
}


