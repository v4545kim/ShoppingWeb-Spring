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

import bean.Member;
import bean.Product;
import controller.common.SuperClass;
import dao.ProductDao;
import shopping.MyCartList;
import shopping.ShoppingInfo;

@Controller
public class MallListController extends SuperClass{
	private final String command = "/list.mall";
	private ModelAndView mav = null;
	
	@Autowired
	@Qualifier("pdao")
	private ProductDao pdao ;
	
	public MallListController() {
		super("MallList", null);
		this.mav = new ModelAndView();
	}
	
	@GetMapping(command)
	public ModelAndView doGet(
			HttpSession session) {
		
		Member loginfo = (Member)session.getAttribute("loginfo");
		
		if (loginfo == null) { // 로그인 하지 않음
			this.mav.setViewName("redirect:/login.me"); // 로그인 페이지로 이동함
			
		} else {
			MyCartList mycart = (MyCartList)session.getAttribute("mycart");
			
			if (mycart == null) { // 쇼핑 내역 없음
				// redirect는 리다이렉션 방식이므로 바인딩 데이터가 휘발됨.
				//	세션으로 데이터 바인딩 요망(common.jsp 파일의 하단부에서 보여줌.)
				String errmsg = "쇼핑 내역이 없어서 상품 목록 페이지로 이동합니다.";
				session.setAttribute("errmsg", errmsg);
				
				this.mav.setViewName("redirect:/list.pr"); // 상품 목록 페이지로 이동
				
			} else { // 쇼핑 내역 있음
				// maplists는 내가 구매할 모든 상품들의 번호와 구매 수량을 저장하고 있는 맵 컬렉션
				Map<Integer, Integer> maplists =  mycart.GetAllOrderLists();
				
				// keylist는 구매하고자 하는 상품들의 상품 번호를 저장하고 있는 Set 구조
				Set<Integer> keylists = maplists.keySet();
				
				// shoplists는 여러 개의 상품에 대한 정보를 저장하고 있는 컬렉션
				List<ShoppingInfo> shoplists = new ArrayList<ShoppingInfo>();
				
				int totalAmount = 0; // 총 판매 금액
				int totalPoint = 0; // 총 적립 포인트
				
				for(Integer pnum : keylists) { // pnum : 상품 번호
					Integer qty = maplists.get(pnum); // 구매 수량
					
					// 상품 번호를 사용하여 Bean 객체 구하기
					Product bean = pdao.SelectDataByPk(pnum);
					
					ShoppingInfo shopinfo = new ShoppingInfo();
					
					int point = bean.getPoint();
					int price = bean.getPrice();
					
					totalAmount += price * qty;
					totalPoint += point * qty;
					
					shopinfo.setImage(bean.getImage());
					shopinfo.setMid(loginfo.getId());
					shopinfo.setPname(bean.getName());
					shopinfo.setPnum(pnum);
					shopinfo.setPoint(point);
					shopinfo.setPrice(price);
					shopinfo.setQty(qty);
					
					shoplists.add(shopinfo);
				}
				// 총 금액과 총 적립될 포인트를 세션 스코프에 바인딩
				session.setAttribute("totalAmount", totalAmount);
				session.setAttribute("totalPoint", totalPoint);
				
				// 이번에 구매할 총 목록
				session.setAttribute("shoplists", shoplists);
				
				this.mav.setViewName(super.getpage); // 장바구니 목록 페이지로 이동
			}

		}
		
		return this.mav;
	}
}
