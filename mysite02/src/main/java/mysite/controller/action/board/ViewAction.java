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

public class ViewAction implements ActionServlet.Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        HttpSession session = request.getSession();
//
//        // Access Control
//        if(session == null) {
//            response.sendRedirect(request.getContextPath());
//            return;
//        }
//        UserVo authUser = (UserVo)session.getAttribute("authUser");
//        if(authUser == null) {
//            response.sendRedirect(request.getContextPath());
//            return;
//        }
//        ///////////////////////////////////////////////////////////
//
//
//        BoardVo vo = new BoardVo();
//        vo.setUserId(authUser.getId());
//
//        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/board/view.jsp");
//        rd.forward(request, response);
    }
}
