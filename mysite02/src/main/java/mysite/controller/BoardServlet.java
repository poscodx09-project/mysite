package mysite.controller;


import mysite.controller.action.board.*;

import javax.servlet.annotation.WebServlet;
import java.util.Map;

@WebServlet("/board")
public class BoardServlet extends ActionServlet {

    private static final long serialVersionUID = 1L;
    private Map<String, Action> mapAction = Map.of(
        "add", new AddAction(),
            "addform", new AddFormAction(),
            "view",new ViewAction(),
            "viewform", new ViewFormAction(),
            "update",new UpdateAction(),
            "updateform", new UpdateFormAction(),
            "delete", new DeleteAction()
    );
    @Override
    protected Action getAction(String actionName) {
        return mapAction.getOrDefault(actionName, new ListAction());
    }
}
