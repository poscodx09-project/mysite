package mysite.controller;

import mysite.security.Auth;
import mysite.service.FileUploadService;
import mysite.service.SiteService;
import mysite.vo.SiteVo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@Auth(role = "ADMIN")
@RequestMapping("/admin")
public class AdminController {

    private final FileUploadService fileUploadService;
    private final SiteService siteService;

    public AdminController(FileUploadService fileUploadService, SiteService siteService){
        this.fileUploadService = fileUploadService;
        this.siteService = siteService;
    }

    @RequestMapping("")
    public String index( Model model){
        SiteVo siteVo = siteService.getSite();
        model.addAttribute("siteVo",siteVo);
        return "admin/main";
    }

    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public String updateFile(@RequestParam("file")MultipartFile file,
                             @RequestParam("title") String title,
                             @RequestParam("description") String description,
                             @RequestParam("welcome") String welcome,
                             Model model ){

        String url = fileUploadService.restore(file);
        System.out.println("file url -------->" + url);
        // SiteVo 객체 생성 및 값 설정
        SiteVo siteVo = new SiteVo();
        siteVo.setTitle(title);
        siteVo.setDescription(description);
        if(url != null){
            siteVo.setProfile(url);
        }
        siteVo.setWelcome(welcome);

        siteService.updateSite(siteVo);

        model.addAttribute("siteVo",siteVo);

        return "main/index";
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
