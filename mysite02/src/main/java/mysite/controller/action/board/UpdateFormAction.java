package mysite.controller.action.board;

import mysite.controller.ActionServlet;
import mysite.dao.BoardDao;
import mysite.dao.UserDao;
import mysite.vo.BoardVo;
import mysite.vo.UserVo;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class UpdateFormAction implements ActionServlet.Action {

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
        int id = Integer.parseInt(request.getParameter("id"));
        BoardVo vo = new BoardDao().findById(id);
        request.setAttribute("vo", vo);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/board/modify.jsp");
        rd.forward(request, response);

    }
}
