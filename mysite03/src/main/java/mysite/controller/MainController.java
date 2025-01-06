package mysite.controller;

import mysite.service.SiteService;
import mysite.vo.SiteVo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
public class MainController {
    private final SiteService siteService;
    public MainController(SiteService siteService){
        this.siteService = siteService;
    }
    @RequestMapping({"","/main"})
    public String index(Model model){

        model.addAttribute("siteVo", siteService.getSite());

        return "main/index";
    }

}
