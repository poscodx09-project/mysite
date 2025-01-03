package mysite.security;

import mysite.vo.UserVo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AuthInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 1. Handler 종류 확인
        if(!(handler instanceof HandlerMethod)){
            // DefaultServletRequestHandler 타입인 경우
            // DefaultServletRequestHandler가 처리하는 경우(정적자원, /assets/**, mapping이 안되어 있는 url)
            return true;
        }
        // 2. Casting
        HandlerMethod handlerMethod = (HandlerMethod) handler;

        // 3. Handler에서 @Auth 가져오기
        Auth auth = handlerMethod.getMethodAnnotation(Auth.class);

        // 4. Handler메소드에서 @Auth가 없으면 클래스(타입)에서 @Auth 가져오기
        if(auth == null){
            auth = handlerMethod.getBeanType().getAnnotation(Auth.class);
        }

        // 5. @Auth가 없으면...
        if(auth == null){ // 인증이 필요없는 경우
            return true;
        }

        // 6. @Auth가 붙어 있기 때문에 인증(Authentication) 여부 확인
        // role에 따라서 실행되는 작업이 달라짐

        HttpSession session = request.getSession();
        UserVo authUser = (UserVo) session.getAttribute("authUser");

        if(authUser == null){
            response.sendRedirect(request.getContextPath() + "/user/login");
            return false;
        }

        String requiredRole = auth.role();
        String userRole = authUser.getRole();
        System.out.println("User Role: " + authUser.getRole());

        // 권한 검증 로직
        if (requiredRole.equals("ADMIN") && (userRole == null || !userRole.equals("ADMIN"))) {
            response.sendRedirect(request.getContextPath());
            return false;
        }
        // 7. @Auth가 붙어 있고 인증도 된 경우
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
