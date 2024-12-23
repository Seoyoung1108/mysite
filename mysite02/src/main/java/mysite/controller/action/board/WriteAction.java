package mysite.controller.action.board;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mysite.controller.ActionServlet.Action;
import mysite.dao.BoardDao;
import mysite.dao.UserDao;
import mysite.vo.BoardVo;
import mysite.vo.UserVo;

public class WriteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String title = request.getParameter("title");
		String contents = request.getParameter("email");
		Long hit = Long.parseLong(request.getParameter("hit")); // 이 아래는 다 바꾸기
		Long gNo = Long.parseLong(request.getParameter("gNo"));
		Long oNo = Long.parseLong(request.getParameter("oNo"));
		Long depth = Long.parseLong(request.getParameter("depth"));
		//Long userId = Long.parseLong(request.getParameter("userId"));
		
		BoardVo vo = new BoardVo();
		vo.setTitle(title);
		vo.setContents(contents);
		vo.setHit(hit);
		vo.setGNo(gNo);
		vo.setONo(oNo);
		vo.setDepth(depth);
		
		BoardDao dao= new BoardDao();
		dao.insert(vo);
		
		response.sendRedirect(request.getContextPath()+"/user?a=joinsuccess");
		
	}

}
