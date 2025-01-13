package mysite.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import mysite.security.Auth;
import mysite.security.AuthUser;
import mysite.service.UserService;
import mysite.vo.UserVo;

@Controller
@RequestMapping("/user")
public class UserController {
	private UserService userService;
	
	public UserController(UserService userService) {
		this.userService=userService;
	}
	
	@RequestMapping(value="/join", method=RequestMethod.GET)
	public String join(@ModelAttribute UserVo userVo) {
		return "user/join";
	}
	
	@RequestMapping(value="/join", method=RequestMethod.POST)
	public String join(@ModelAttribute @Valid UserVo userVo, BindingResult result, Model model) {
		if(result.hasErrors()) {
			Map<String,Object> map = result.getModel();
			model.addAllAttributes(map);
			
			return "user/join";
		}
		
		userService.join(userVo);
		
		return "redirect:/user/joinsuccess";
	}
	
	@RequestMapping("/joinsuccess")
	public String joinSuccess() {
		return "user/joinsuccess";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String login() {
		return "user/login";
	}
	
	// HttpSession session 있으면 interceptor로
	
	/*
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String login(HttpSession session, UserVo vo, Model model) {
		UserVo authUser = userService.getUser(vo.getEmail(),vo.getPassword()); // 보안 처리는 나중에
		if(authUser==null) {
			model.addAttribute("email", vo.getEmail());
			model.addAttribute("result", "fail");
			
			return "user/login";
		}
		
		// login 처리
		session.setAttribute("authUser", authUser);
		
		return "redirect:/";
	}
	
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("authUser");
		session.invalidate();
		
		return "redirect:/";
	}
	
	@RequestMapping(value="/update", method=RequestMethod.GET)
	public String update(HttpSession session, Model model) {
		// Access Control		
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser==null) {
			return "redirect:/";
		}
		
		UserVo vo = userService.getUser(authUser.getId());
		
		model.addAttribute("vo",vo);
		return "user/update";
	}
	
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public String update(HttpSession session, UserVo vo) {
		// Access Control
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser==null) {
			return "redirect:/";
		}
		
		vo.setId(authUser.getId());
		userService.update(vo);
		
		authUser.setName(vo.getName());
		
		return "redirect:/user/update";
	}
	*/
	
	@Auth
	@RequestMapping(value="/update", method=RequestMethod.GET)
	public String update(@AuthUser UserVo authUser, Model model) {		
		UserVo vo = userService.getUser(authUser.getId());
		
		model.addAttribute("vo",vo);
		return "user/update";
	}
	
	@Auth
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public String update(@AuthUser UserVo authUser, UserVo vo) {
		vo.setId(authUser.getId());
		userService.update(vo);
		
		authUser.setName(vo.getName());
		
		return "redirect:/user/update";
	}
}

