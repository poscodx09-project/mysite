package mysite.controller;

import com.sun.jna.platform.win32.Netapi32Util;
import mysite.service.SiteService;
import mysite.vo.SiteVo;
import mysite.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class MainController {
    private final SiteService siteService;

    public MainController(SiteService siteService){
        this.siteService = siteService;
    }
    @RequestMapping({"","/main"})
    public String index(HttpServletRequest request, Model model){

//        model.addAttribute("siteVo", siteService.getSite());

        return "main/index";
    }

    @ResponseBody
    @RequestMapping("/msg01")
    public String message01(){
        return "Hello, EunJae Lee~";
    }

    @ResponseBody
    @RequestMapping("/msg02")
    public String message02(){
        return "Bye, EunJae Lee~ \n";
    }

    @ResponseBody
    @RequestMapping("/msg03")
    public UserVo message03(){
        UserVo vo = new UserVo();
        vo.setId(10L);
        vo.setName("scone");
        vo.setEmail("test@test.com");
        return vo;
    }

}
