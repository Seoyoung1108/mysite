package mysite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.LocaleResolver;

import jakarta.servlet.http.HttpServletRequest;
import mysite.service.SiteService;
import mysite.vo.SiteVo;
import mysite.vo.UserVo;


@Controller
public class MainController {
	@Autowired
	ApplicationContext applicationContext;
	
	/*
	private SiteService siteService;

	public MainController(SiteService siteService) {
		this.siteService=siteService;
	}
	*/
	@RequestMapping({"/","/main"})
	public String index(Model model) {
		//SiteVo vo = applicationContext.getBean(SiteVo.class);
		//System.out.println(vo);
		return "main/index";
	}
	
	@RequestMapping("/ex")
	public String ex() {
		return "example/01";
	}
	
	@ResponseBody
	@RequestMapping("/msg01")
	public String message01() {
		return "Hello World!";
	}
	
	@ResponseBody
	@RequestMapping("/msg02")
	public String message02() {
		return "안녕!";
	}
	
	@ResponseBody
	@RequestMapping("/msg03")
	public Object message03() {
		UserVo vo = new UserVo();
		vo.setId(10L);
		vo.setName("aaa");
		vo.setEmail("aaa@naver.com");
		
		return vo;
	}
}
