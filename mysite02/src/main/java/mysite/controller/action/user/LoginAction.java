package mysite.controller.action.user;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mysite.controller.ActionServlet.Action;
import mysite.dao.UserDao;
import mysite.vo.UserVo;

public class LoginAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		
		UserVo vo = new UserDao().findByEmailAndPassword(email,password);
		
		// 로그인 실패
		if(vo==null) {
			// response.sendRedirect("/mysite02/user?a=loginform&result=fail");
			request.setAttribute("result", "fail");
			request.setAttribute("email", email);
			
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/user/loginform.jsp");
			rd.forward(request, response);
			
			return;
		}
		
		// 로그인 처리
		
		
		
		/*
		vo.setName(name);
		vo.setEmail(email);
		vo.setPassword(password);
		vo.setGender(gender);
		
		UserDao dao= new UserDao();
		dao.insert(vo);
		
		response.sendRedirect("/mysite02/user?a=joinsuccess");*/

	}

}
