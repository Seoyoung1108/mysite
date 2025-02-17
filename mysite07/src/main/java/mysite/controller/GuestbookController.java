package mysite.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import mysite.vo.GuestbookVo;
import mysite.service.GuestbookService;

@Controller
@RequestMapping("/guestbook")
public class GuestbookController {
	private GuestbookService guestbookService;
	
	public GuestbookController(GuestbookService guestbookService) {
		this.guestbookService=guestbookService;
	}
	
	@RequestMapping("")
	public String list(Model model) {	
		List<GuestbookVo> list = guestbookService.getContentsList();
		model.addAttribute("list", list);
		model.addAttribute("newLine", "\n");
		return "guestbook/list";
	}
	
	@RequestMapping("/add")
	public String add(GuestbookVo vo) {
		guestbookService.addContents(vo);
		return "redirect:/guestbook";
	}
	
	@RequestMapping(value="/delete/{no}", method=RequestMethod.GET)
	public String delete(@PathVariable("no") Long no, Model model) {
		model.addAttribute("no", no);
		return "guestbook/delete";
	}
	
	@RequestMapping(value="/delete/{no}", method=RequestMethod.POST)
	public String delete(@PathVariable("no") Long no, @RequestParam(value="password",required=true,defaultValue="") String password) {
		guestbookService.deleteContents(no,password);
		return "redirect:/guestbook";
	}
}
