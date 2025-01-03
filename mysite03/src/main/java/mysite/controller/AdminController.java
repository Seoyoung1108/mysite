package mysite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


import mysite.security.Auth;
import mysite.service.BoardService;
import mysite.service.FileuploadService;
import mysite.service.SiteService;
import mysite.vo.SiteVo;

@Auth(role="ADMIN")
@Controller
@RequestMapping("/admin")
public class AdminController {
	private SiteService siteService;
	private FileuploadService fileuploadService;
	
	public AdminController(SiteService siteService, FileuploadService fileuploadService) {
		this.siteService=siteService;
		this.fileuploadService=fileuploadService;
	}
	
	@RequestMapping({"","/main"})
	public String main(Model model) {
		SiteVo vo = siteService.getSite();
		model.addAttribute("vo", vo);
		return "admin/main";
	}

	@RequestMapping("/main/update")
	public String mainUpdate(@RequestParam("title") String title, @RequestParam("welcomeMessage") String welcome, @RequestParam("description") String description, @RequestParam("file") MultipartFile file) {
		SiteVo vo = new SiteVo();
		String url = fileuploadService.restore(file);
		if(url != null) {
			vo.setProfile(url);
		}
		vo.setTitle(title);
		vo.setWelcome(welcome);
		vo.setDescription(description);
		
		siteService.updateSite(vo);
		return "redirect:/admin";
	}
	
	@RequestMapping("/guestbook")
	public String guestbook() {
		return "admin/guestbook";
	}
	
	@RequestMapping("/board")
	public String board() {
		return "admin/board";
	}
	
	@RequestMapping("/user")
	public String user() {
		return "admin/user";
	}
}
