package mysite.controller;

import mysite.security.Auth;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Auth(role = "ADMIN")
@RequestMapping("/admin")
public class AdminController {

    @RequestMapping({"","/main"})
    public String main(){
        return "admin/main";
    }


    @RequestMapping("/guestbook")
    public String guestBook(){
        return "admin/guestbook";
    }


    @RequestMapping("/board")
    public String board(){
        return "admin/board";
    }


    @RequestMapping("/user")
    public String user(){
        return "admin/user";
    }

}
