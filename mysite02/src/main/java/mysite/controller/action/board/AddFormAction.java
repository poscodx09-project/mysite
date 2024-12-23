package mysite.controller.action.board;

import mysite.controller.ActionServlet;
import mysite.vo.UserVo;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AddFormAction implements ActionServlet.Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        // Access Control
        if(session == null) {
            response.sendRedirect(request.getContextPath());
            return;
        }

        UserVo authUser = (UserVo)session.getAttribute("authUser");
        if(authUser == null) {
            response.sendRedirect(request.getContextPath());
            return;
        }
        //////////////////////////////////////////////////////////
        String type = request.getParameter("type"); // "original" or "reply"
        request.setAttribute("type", type);
        if(type.equals("reply")){
            Long id = Long.parseLong(request.getParameter("id")); // "original" or "reply"
            request.setAttribute("id", id);
        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/board/write.jsp");
        rd.forward(request, response);

    }
}
