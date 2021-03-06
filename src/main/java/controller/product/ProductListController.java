package controller.product;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import bean.Product;
import controller.common.SuperClass;
import dao.ProductDao;
import utility.FlowParameters;
import utility.Paging;

@Controller
public class ProductListController extends SuperClass{
	private final String command = "/list.pr" ; 
	private ModelAndView mav = null ;
	private String redirect = "redirect:/list.pr" ;
	
	@Autowired
	@Qualifier("pdao")
	private ProductDao dao ;
	
	public ProductListController() {
		super("prList", "prList");
		this.mav = new ModelAndView();
	}
	
	@GetMapping(command)
	public ModelAndView doGet(
			HttpServletRequest request,
			@RequestParam(value = "pageNumber", required = false) String pageNumber, 
			@RequestParam(value = "pageSize", required = false) String pageSize,
			@RequestParam(value = "mode", required = false) String mode,
			@RequestParam(value = "keyword", required = false) String keyword){
		
		FlowParameters parameters 
		= new FlowParameters(pageNumber, pageSize, mode, keyword);
	
		System.out.println(this.getClass() + " : " + parameters.toString());
		
		int totalCount 
				= dao.SelectTotalCount(
						parameters.getMode(), 
						parameters.getKeyword() + "%");
		
		String contextpath = request.getContextPath() + "/" ;
		String myurl = contextpath +  this.command ;
		
		Paging pageInfo = new Paging(
				parameters.getPageNumber(), 
				parameters.getPageSize(), 
				totalCount, 
				myurl, 
				parameters.getMode(), 
				parameters.getKeyword()) ;
		
		List<Product> lists = dao.SelectDataList(
				pageInfo.getOffset(),
				pageInfo.getLimit(),
				parameters.getMode(), 
				parameters.getKeyword() + "%"); 
		
		
		// ?????? ????????? ?????????
		mav.addObject("lists", lists);
		
		System.out.println("?????? ?????? ?????? : " + lists.size());
		
		// ????????? ?????? ?????????
		mav.addObject("pagingHtml", pageInfo.getPagingHtml());
		mav.addObject("pagingStatus", pageInfo.getPagingStatus());
		
		// ?????? ????????? ????????? ?????????
		mav.addObject("mode", parameters.getMode());
		mav.addObject("keyword", parameters.getKeyword());
		
		// ???????????? ????????? ????????? : ????????????, ??????, ??????, ?????? ?????? ?????????
		mav.addObject("parameters", parameters.toString());		
		
		this.mav.setViewName(super.getpage);
		return this.mav ;
	}
	
	@PostMapping(command)
	public ModelAndView doPost(){
		this.mav.setViewName(super.postpage);
		return this.mav ;
	}
}