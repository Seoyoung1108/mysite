package mysite.controller;

import jakarta.servlet.annotation.WebServlet;
import mysite.controller.action.board.ListAction;
import mysite.controller.action.board.WriteAction;
import mysite.controller.action.board.WriteFormAction;

import java.util.Map;

@WebServlet("/board")
public class BoardServlet extends ActionServlet {
	private static final long serialVersionUID = 1L;
	
	private Map<String, Action> mapAction = Map.of(
			"writeform", new WriteFormAction(),
			"write", new WriteAction());
	
	/*
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}*/

	@Override
	protected Action getAction(String actionName) {
		return mapAction.getOrDefault(actionName, new ListAction());

	}

}
