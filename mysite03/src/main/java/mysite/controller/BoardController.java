package mysite.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import mysite.service.BoardService;
import mysite.vo.BoardVo;
import mysite.vo.UserVo;

@Controller
@RequestMapping("/board")
public class BoardController {
	private BoardService boardService;
	
	public BoardController(BoardService boardService) {
		this.boardService=boardService;
	}
	
	@RequestMapping("")
	public String list(Model model) {	
		List<BoardVo> list = boardService.getContentsList();
		model.addAttribute("list", list);
		return "/board/list";
	}
	
	@RequestMapping(value="/view/{id}", method=RequestMethod.GET)
	public String view(@PathVariable("id") Long id, Model model) {
		BoardVo vo = boardService.getContents(id);
		model.addAttribute("id", id);
		model.addAttribute("vo", vo);
		return "/board/view";
	}
	
	@RequestMapping(value="/write", method=RequestMethod.GET)
	public String write(HttpSession session) {
		// Access Control
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser==null) {
			return "redirect:/";
		}
		return "/board/write";
	}
	
	@RequestMapping(value="/write", method=RequestMethod.POST)
	public String write(HttpSession session, BoardVo vo) {
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		
		vo.setUserId(authUser.getId());
		boardService.addContents(vo);
		return "redirect:/board";
	}
	
	@RequestMapping(value="/reply/{id}", method=RequestMethod.GET)
	public String reply(HttpSession session, @PathVariable("id") Long id, Model model) {
		// Access Control
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser==null) {
			return "redirect:/";
		}
		
		BoardVo vo = boardService.getContents(id);
		model.addAttribute("id",id);
		model.addAttribute("vo",vo);
		
		return "/board/reply";
	}
	
	@RequestMapping(value="/reply/{id}", method=RequestMethod.POST)
	public String reply(HttpSession session, @PathVariable("id") Long id, BoardVo vo) {
		// Access Control
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser==null) {
			return "redirect:/";
		}
		
		BoardVo parentVo = boardService.getContents(id);
		vo.setGNo(parentVo.getGNo());
		vo.setONo(parentVo.getONo()+1);
		vo.setDepth(parentVo.getDepth()+1);
		vo.setUserId(authUser.getId());
		boardService.replyContents(vo);
		
		return "redirect:/board";
	}
	
	@RequestMapping(value="/modify/{id}", method=RequestMethod.GET)
	public String modify(HttpSession session, @PathVariable("id") Long id, Model model) {
		// Access Control
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser==null) {
			return "redirect:/";
		}
		
		BoardVo vo = boardService.getContents(id);
		model.addAttribute("id",id);
		model.addAttribute("vo",vo);
		return "board/modify";
	}
	
	@RequestMapping(value="/modify/{id}", method=RequestMethod.POST)
	public String modify(HttpSession session, @PathVariable("id") Long id, BoardVo vo) {
		// Access Control
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser==null) {
			return "redirect:/";
		}
		
		vo.setId(id);
		boardService.updateContents(vo);
		
		return "redirect:/board/view/{id}";
	}
	
	@RequestMapping(value="/delete/{id}", method=RequestMethod.GET)
	public String delete(HttpSession session, @PathVariable("id") Long id, Model model) {
		// Access Control
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser==null) {
			return "redirect:/";
		}
				
		model.addAttribute("id", id);
		return "/board/delete";
	}
	
	@RequestMapping(value="/delete/{id}", method=RequestMethod.POST)
	public String delete(HttpSession session, @PathVariable("id") Long id) {
		// Access Control
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser==null) {
			return "redirect:/";
		}
				
		boardService.deleteContents(id);
		return "redirect:/board";
	}
}
