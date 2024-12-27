package mysite.controller;

import mysite.service.GuestBookService;
import mysite.vo.GuestbookVo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/guestbook")
public class GuestBookController {

    private final GuestBookService guestBookService;
    public GuestBookController(GuestBookService guestBookService){
        this.guestBookService = guestBookService;
    }

    @RequestMapping
    public String index(Model model) {
        List<GuestbookVo> list = guestBookService.getContentsList();
        model.addAttribute("list", list);
        return "guestbook/list";
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public String add(GuestbookVo vo) {
        guestBookService.addContents(vo);
        return "redirect:/guestbook";
    }

    @RequestMapping(value="/delete/{id}", method=RequestMethod.GET)
    public String delete(@PathVariable(name = "id") Long id) {
        return "guestbook/delete";
    }

    @RequestMapping(value = "/delete/{id}",method = RequestMethod.POST)
    public String delete(@PathVariable(name = "id") Long id, @RequestParam("password") String password) {
        System.out.println("출력  " + id + ":" +password);
        guestBookService.deleteContents(id,password);
        return "redirect:/guestbook";
    }
}
