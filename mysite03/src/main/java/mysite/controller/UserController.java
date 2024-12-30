package mysite.controller;

import mysite.security.Auth;
import mysite.security.AuthUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import mysite.service.UserService;
import mysite.vo.UserVo;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value="/join", method=RequestMethod.GET)
    public String join() {
        return "user/join";
    }

    @RequestMapping(value="/join", method=RequestMethod.POST)
    public String join(UserVo userVo) {
        userService.join(userVo);
        return "redirect:/user/joinsuccess";
    }

    @RequestMapping("/joinsuccess")
    public String joinSuccess() {
        return "user/joinsuccess";
    }

    @RequestMapping(value="/login", method=RequestMethod.GET)
    public String login() {
        return "user/login";
    }

//    @RequestMapping(value="/login", method=RequestMethod.POST)
//    public String login(HttpSession session, UserVo userVo, Model model) {
//        UserVo authUser = userService.getUser(userVo.getEmail(), userVo.getPassword());
//        if(authUser == null) {
//            model.addAttribute("email", userVo.getEmail());
//            model.addAttribute("result", "fail");
//            return "user/login";
//        }
//
//        session.setAttribute("authUser", authUser);
//        return "redirect:/";
//    }
//
//    @RequestMapping("/logout")
//    public String logout(HttpSession session) {
//        session.removeAttribute("authUser");
//        session.invalidate();
//
//        return "redirect:/";
//    }

    @Auth
    @RequestMapping(value="/update", method=RequestMethod.GET)
    public String update(@AuthUser UserVo authUser, Model model) {
        // Access Control
//        UserVo authUser = (UserVo)session.getAttribute("authUser");
//        if(authUser == null) {
//            return "redirect:/";
//        }

        UserVo userVo = userService.getUser(authUser.getId());

        model.addAttribute("vo", userVo);
        return "user/update";
    }

    @Auth
    @RequestMapping(value="/update", method=RequestMethod.POST)
    public String update(@AuthUser UserVo authUser, UserVo userVo) {
        // Access Control
        UserVo authUser = (UserVo)session.getAttribute("authUser");
        if(authUser == null) {
            return "redirect:/";
        }

        userVo.setId(authUser.getId());
        userService.update(userVo);

        authUser.setName(userVo.getName());
        return "redirect:/user/update";
    }

}