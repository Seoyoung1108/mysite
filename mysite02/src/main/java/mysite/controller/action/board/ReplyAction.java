package mysite.controller.action.board;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import mysite.controller.ActionServlet.Action;
import mysite.dao.BoardDao;
import mysite.vo.BoardVo;
import mysite.vo.UserVo;

public class ReplyAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		
		Long parentGNo = Long.parseLong(request.getParameter("parentGNo"));
		Long prevONo = Long.parseLong(request.getParameter("prevONo"));
		Long parentDepth = Long.parseLong(request.getParameter("parentDepth"));
		String title = request.getParameter("title");
		String contents = request.getParameter("contents");
		
		BoardVo vo = new BoardVo();
		vo.setTitle(title);
		vo.setContents(contents);
		vo.setGNo(parentGNo);
		vo.setONo(prevONo+1);
		vo.setDepth(parentDepth+1);
		vo.setUserId(authUser.getId());
		
		BoardDao dao= new BoardDao();
		dao.reply(vo);
		
		response.sendRedirect(request.getContextPath()+"/board");
	}

}
