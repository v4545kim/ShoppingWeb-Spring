package controller.mall;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import bean.Member;
import bean.Order;
import controller.common.SuperClass;
import dao.MallDao;
import shopping.MyCartList;
import shopping.ShoppingInfo;

@Controller
public class MallDetailController extends SuperClass{
	private final String command = "/detailview.mall";
	private ModelAndView mav = null;
	private final String redirect = "redirect:/수정02.mall";
	
	@Autowired
	@Qualifier("malldao")
	private MallDao malldao ;
	
	public MallDetailController() {
		super("ShopResult", null);
		this.mav = new ModelAndView();
	}
	
	
	@GetMapping(command)
	public ModelAndView doGet(
			@RequestParam(value = "oid", required = true) int oid,
			HttpSession session) {
		
		Member loginfo = (Member)session.getAttribute("loginfo");
		if (loginfo == null) {
			this.mav.setViewName("redirect:/login.me");
		} else {
			System.out.println("해당 주문 정보에 대한 상세 내역을 출력합니다.");
			Order order = malldao.SelectDataByPk(oid);
			
			List<ShoppingInfo> lists = malldao.ShowDetail(oid);

			System.out.println("주문 상세 내역 개수 : " + lists.size());
			
			this.mav.addObject("order", order); // 주문 정보
			
			this.mav.addObject("lists", lists); // 쇼핑 세부 정보
			
			this.mav.setViewName(super.getpage);
		}
		
		
		MyCartList mycart = (MyCartList)session.getAttribute("mycart");
		
		return this.mav;
	}
}


