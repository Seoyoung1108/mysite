package mysite.controller.action.board;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mysite.controller.ActionServlet.Action;
import mysite.dao.BoardDao;
import mysite.vo.BoardVo;

public class DeleteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Long id = Long.parseLong(request.getParameter("n"));
		Long gNo = Long.parseLong(request.getParameter("gNo"));
		Long oNo = Long.parseLong(request.getParameter("oNo"));
		System.out.println(id+gNo+oNo);
		new BoardDao().delete(id,gNo,oNo);
		
		response.sendRedirect(request.getContextPath()+"/board");

	}

}
