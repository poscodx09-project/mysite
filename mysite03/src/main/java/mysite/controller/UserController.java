package mysite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import mysite.security.Auth;
import mysite.security.AuthUser;
import mysite.service.UserService;
import mysite.vo.UserVo;

@Controller
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value="/join", method=RequestMethod.GET)
    public String join(@ModelAttribute UserVo userVo) {
        return "user/join";
    }

    @RequestMapping(value="/join", method=RequestMethod.POST)
    public String join(@ModelAttribute @Valid UserVo userVo, BindingResult result, Model model) {
        if(result.hasErrors()) {
            model.addAllAttributes(result.getModel());
            return "user/join";
        }

        System.out.println(userVo);
        userService.join(userVo);
        // id(PK) set 확인
        System.out.println(userVo);

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

    @Auth
    @RequestMapping(value="/update", method=RequestMethod.GET)
    public String update(@AuthUser UserVo authUser, Model model) {
        UserVo userVo = userService.getUser(authUser.getId());

        model.addAttribute("vo", userVo);
        return "user/update";
    }

    @Auth
    @RequestMapping(value="/update", method=RequestMethod.POST)
    public String update(@AuthUser UserVo authUser, UserVo userVo) {
        userVo.setId(authUser.getId());
        userService.update(userVo);

        authUser.setName(userVo.getName());
        return "redirect:/user/update";
    }
}