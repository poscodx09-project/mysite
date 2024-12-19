package mysite.controller.action.user;

import mysite.controller.ActionServlet;
import mysite.controller.ActionServlet.Action;
import mysite.dao.UserDao;
import mysite.vo.UserVo;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class UpdateAction implements Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        UserVo vo = (UserVo)session.getAttribute("authUser");

        Long id = vo.getId();
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        String gender = request.getParameter("gender");
        System.out.println("들어온 성별--------->"+gender);

        UserVo updatedVo = new UserVo();
        updatedVo.setId(id);
        updatedVo.setName(name);
        updatedVo.setPassword(password);
        updatedVo.setGender(gender);

        if(password != ""){
            new UserDao().updateWithPassword(id,updatedVo);
        }else{
            new UserDao().updateWithOutPassword(id,updatedVo);
        }

        session.setAttribute("authUser", updatedVo);

        response.sendRedirect(request.getContextPath());
    }
}
