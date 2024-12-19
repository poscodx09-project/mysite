package mysite.controller;

import javax.servlet.annotation.WebServlet;
import mysite.controller.action.main.MainAction;

@WebServlet({"/main", ""})
public class MainServlet extends ActionServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected Action getAction(String actionName) {
		return new MainAction();
	}
}