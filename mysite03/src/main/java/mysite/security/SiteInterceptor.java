package mysite.security;

import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import mysite.service.SiteService;
import mysite.vo.SiteVo;

public class SiteInterceptor implements HandlerInterceptor {
	private SiteService siteService;

	public SiteInterceptor(SiteService siteService) {
		this.siteService=siteService;
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		SiteVo vo = siteService.getSite();
		request.setAttribute("title", vo.getTitle());
		
		return true;
	}

}
