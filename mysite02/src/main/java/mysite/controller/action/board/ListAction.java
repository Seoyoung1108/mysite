package mysite.controller.action.board;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mysite.controller.ActionServlet.Action;
import mysite.dao.BoardDao;
import mysite.vo.BoardVo;

public class ListAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		String p = (String)Optional.ofNullable(request.getParameter("p")).orElse("1");
		int page = Integer.parseInt(p);
		List<BoardVo> list = new BoardDao().findByPage(page);
		
		int count = new BoardDao().findAll();
		int col= (page-1)/5+1;
		
		request.setAttribute("col", col);
		request.setAttribute("pick", page);
		request.setAttribute("pageCount", count/5+1);
		
		//List<BoardVo> list = new BoardDao().findAll();
		request.setAttribute("list", list);
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/board/list.jsp");
		rd.forward(request, response);
	}

}
