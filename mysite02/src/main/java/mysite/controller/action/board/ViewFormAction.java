package mysite.controller.action.board;

import mysite.controller.ActionServlet;
import mysite.dao.BoardDao;
import mysite.vo.BoardVo;
import mysite.vo.UserVo;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ViewFormAction implements ActionServlet.Action {
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
        System.out.println("id 가 파라미터로 잘 전달되고 있나요~?" + id);
        BoardVo vo = new BoardDao().findById(id);
        new BoardDao().incrementHit(id);
        request.setAttribute("vo", vo);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/board/view.jsp");
        rd.forward(request, response);

    }
}
