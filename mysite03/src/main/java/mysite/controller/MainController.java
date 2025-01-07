package mysite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.LocaleResolver;

import jakarta.servlet.http.HttpServletRequest;
import mysite.service.SiteService;
import mysite.vo.SiteVo;


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
	public String index(Model model, HttpServletRequest request) {
		SiteVo vo = applicationContext.getBean(SiteVo.class);
		System.out.println(vo);
		return "main/index";
	}
}
