package controller.mall;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import bean.Order;
import controller.common.SuperClass;
import dao.CompositeDao;
import dao.MallDao;
import shopping.ShoppingInfo;

@Controller
public class MallDetailController extends SuperClass{
	private final String command = "/detailview.mall" ; 
	private ModelAndView mav = null ;
	//private String redirect = "redirect:/detailview.mall" ;
	
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
			HttpSession session){
		
		if (session.getAttribute("loginfo") == null) {
			this.mav.setViewName("redirect:/login.me");
		} else {			
			//Order 주문 정보 가져 오기
			Order order = malldao.SelectDataByPk(oid) ;
			
			//lists : 해당 송장 번호에 대한 주문 상세 내역을 보여 주세요
			List<ShoppingInfo> lists = malldao.ShowDetail( oid ) ;
			
			System.out.println("주문 상세 내역 갯수 " + lists.size());

			this.mav.addObject("order", order); //주문 정보			
			this.mav.addObject("lists", lists); //쇼핑 정보
			this.mav.setViewName(super.getpage);			
		}
		return this.mav ;
	}
}