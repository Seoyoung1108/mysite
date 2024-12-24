package mysite.controller.action.board;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mysite.controller.ActionServlet.Action;
import mysite.dao.BoardDao;
import mysite.vo.BoardVo;

public class ViewAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Long id = Long.parseLong(request.getParameter("n"));
		
		BoardVo vo = new BoardDao().findById(id);
		request.setAttribute("vo", vo);
		
		// cookie 조회수
		Long visitCount=0L;
		
		// 쿠키 읽기
		Cookie[] cookies = request.getCookies();
		
		if(cookies != null && cookies.length>0) {
			for(Cookie cookie: cookies) {
				if("visitCount".equals(cookie.getName())) {
					visitCount = Long.parseLong(cookie.getValue());
				}
			}
		}
		
		visitCount++;
		
		BoardVo vo1 = new BoardVo();
		vo1.setId(id);
		vo1.setHit(vo.getHit()+visitCount);
		new BoardDao().hitUp(vo1);
		
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/board/view.jsp");
		rd.forward(request, response);
	}

}
