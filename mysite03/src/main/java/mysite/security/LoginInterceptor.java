package mysite.security;

import com.sun.jna.platform.win32.Netapi32Util;
import mysite.service.UserService;
import mysite.vo.UserVo;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptor implements HandlerInterceptor {
    private UserService userService;
    public LoginInterceptor(UserService userService){
        this.userService = userService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        UserVo authUser = userService.getUser(email,password);

        if(authUser == null){
            request.setAttribute("email",email);
            request.setAttribute("result","fail");
            request.getRequestDispatcher("/WEB-INF/views/user/login.jsp").forward(request,response);
            return false;
        }
        /* 로그인 처리 */
        HttpSession session = request.getSession();
        session.setAttribute("authUser",authUser);
        System.out.println("role =>" + authUser.getRole());

        response.sendRedirect(request.getContextPath());

        return false;
    }

}
