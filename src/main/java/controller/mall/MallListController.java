package controller.mall;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import bean.Product;
import controller.common.SuperClass;
import dao.ProductDao;
import shopping.MyCartList;
import shopping.ShoppingInfo;

@Controller
public class MallListController extends SuperClass{
	private final String command = "/list.mall" ; 
	private ModelAndView mav = null ;
	//private String redirect = "redirect:/list.mall" ;
	
	@Autowired
	@Qualifier("pdao")
	private ProductDao pdao ;
	
	public MallListController() {
		super("MallList", null);
		this.mav = new ModelAndView();
	}
	
	@GetMapping(command)
	public ModelAndView doGet(
			HttpSession session){	
		if (session.getAttribute("loginfo") == null) {			
			this.mav.setViewName("redirect:/login.me"); 
		} else {
			MyCartList mycart = (MyCartList)session.getAttribute("mycart") ;
			if (mycart==null) {
				String errmsg = "쇼핑 내역이 없어서 상품 목록으로 이동합니다." ;
				this.mav.addObject("errmsg", errmsg );
				this.mav.setViewName("redirect:/list.pr");
			}else {
				Map<Integer, Integer> maplists =  mycart.GetAllOrderLists() ;
				
				// keylist : 구매하고자 하는 상품 번호를 저장하고 있는 Set 자료 구조
				Set<Integer> keylist = maplists.keySet() ;
				
				// ShoppingInfo : 상품 1건에 대한 정보를 저장하고 있는 Bean 객체
				// shoplists : ShoppingInfo 객체들을 저장하고 있는 컬렉션 객체
				List<ShoppingInfo> shoplists = new ArrayList<ShoppingInfo>();
				
				int totalAmount = 0 ; // 총 판매 금액
				int totalPoint = 0 ; // 총 누적 포인트
				
				for(Integer pnum :  keylist){  // pnum : 상품 번호
					Integer qty = maplists.get(pnum) ;// 구매 수량
					
					// 상품 번호 pnum에 대한 Bean 정보				 				
					Product bean = pdao.SelectDataByPk(pnum) ;
					
					ShoppingInfo shopinfo = new ShoppingInfo() ;
					
					int point = bean.getPoint() ;
					int price = bean.getPrice() ;
					
					totalAmount += qty * price ;
					totalPoint += qty * point ;
					
					shopinfo.setImage(bean.getImage());
					shopinfo.setPname(bean.getName()); 
					shopinfo.setPnum(pnum);
					shopinfo.setPoint(point); 
					shopinfo.setPrice(price); 
					shopinfo.setQty(qty);  
					
					shoplists.add(shopinfo) ;
				}
				
				session.setAttribute("totalAmount", totalAmount) ;
				session.setAttribute("totalPoint", totalPoint) ;
				
				// 이번에 구매할 총 목록
				session.setAttribute("shoplists", shoplists) ;
				
				this.mav.setViewName(super.getpage);
			}
		}	
		return this.mav ;
	}
}