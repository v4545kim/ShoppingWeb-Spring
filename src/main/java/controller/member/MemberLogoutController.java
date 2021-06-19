package controller.member;

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
import dao.MemberDao;
import dao.ProductDao;
import shopping.MyCartList;
import shopping.ShoppingInfo;

@Controller
public class MemberLogoutController extends SuperClass{
	private final String command = "/logout.me";
	private ModelAndView mav = null;
	
	@Autowired
	@Qualifier("pdao")
	private ProductDao pdao ;
	
	@Autowired
	@Qualifier("mdao")
	private MemberDao mdao ;
	
	public MemberLogoutController() {
		super("meLoginForm","boList");
		this.mav = new ModelAndView();
	}
	
	@GetMapping(command)
	public ModelAndView doGet(HttpSession session) {
		
		// 세션 공간에 장바구니 정보가 있으면
		//	장바구니 임시 테이블에 저장합니다.
		MyCartList mycart = (MyCartList)session.getAttribute("mycart");
		
		if (mycart != null) {
			Map<Integer, Integer> maplists = mycart.GetAllOrderLists();
			
			Member mem = (Member)session.getAttribute("loginfo");
			
			System.out.println("로그 아웃 중이시군요.");
			System.out.println("장바구니 품목 사이즈 : " + maplists.size());
			
			Set<Integer> keylists = maplists.keySet();
			
			List<ShoppingInfo> lists = new ArrayList<ShoppingInfo>();
			
			for(Integer pnum : keylists) {
				Product bean = pdao.SelectDataByPk(pnum);
				
				ShoppingInfo shopInfo = new ShoppingInfo();
				shopInfo.setImage(bean.getImage());
				shopInfo.setMid(mem.getId());
				shopInfo.setPname(bean.getName());
				shopInfo.setPnum(pnum);
				shopInfo.setPoint(bean.getPoint());
				shopInfo.setPrice(bean.getPrice());
				shopInfo.setQty(maplists.get(pnum));
				
				lists.add(shopInfo);
			}
			
			this.mdao.InsertCartData(mem, lists);
		}
		
		// 세션 영역을 완전히 삭제하도록 합니다.
		session.invalidate();
		
		// 로그인 페이지로 다시 이동합니다.
		this.mav.setViewName(super.getpage);
		return this.mav;
	}
}
