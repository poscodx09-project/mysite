package mysite.controller.action.board;

import mysite.controller.ActionServlet;
import mysite.dao.BoardDao;
import mysite.dao.UserDao;
import mysite.vo.BoardVo;
import mysite.vo.UserVo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class UpdateAction implements ActionServlet.Action {
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
        ///////////////////////////////////////////////////////////

        String title = request.getParameter("title");
        String contents = request.getParameter("content");
        Long id = Long.parseLong(request.getParameter("id"));

        BoardVo vo = new BoardVo();
        vo.setTitle(title);
        vo.setContents(contents);
        vo.setId(id);

        new BoardDao().update(vo);

        response.sendRedirect(request.getContextPath() + "/board?updateform");

    }
}
