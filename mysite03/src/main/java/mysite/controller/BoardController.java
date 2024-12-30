package mysite.controller;

import mysite.security.Auth;
import mysite.security.AuthUser;
import mysite.service.BoardService;
import mysite.vo.BoardVo;
import mysite.vo.GuestbookVo;
import mysite.vo.UserVo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/board")
@Auth
public class BoardController {
    private final BoardService boardService;
    public BoardController(BoardService boardService){
        this.boardService = boardService;
    }

    @RequestMapping
    public String board(Model model,@RequestParam(value = "page",required = false,defaultValue = "1") Integer page,@RequestParam(value = "kwd", required = false)String keyword){
        Map<String,Object > res = boardService.getContentsList(page,keyword);

        model.addAttribute("boardList", res.get("boardList"));
        model.addAttribute("beginPage", res.get("beginPage"));
        model.addAttribute("currentPage", res.get("currentPage"));
        model.addAttribute("endPage", res.get("endPage"));
        model.addAttribute("totalPages", res.get("totalPages"));

        model.addAttribute("kwd",keyword);
        model.addAttribute("page",page);

        return "board/list";
    }

    @RequestMapping(value = "/add",method = RequestMethod.GET)
    public String add(@RequestParam("type") String type, @RequestParam(value = "id", required = false) Long id, Model model){
        // type 값 및 기본 데이터 전달
        model.addAttribute("type", type);
        model.addAttribute("id", id);
        return "board/write";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(@AuthUser UserVo authUser,
                      BoardVo vo,
                      @RequestParam("type") String type,
                      @RequestParam(value = "id", required = false) Long id,
                      Model model) {

        if ("reply".equals(type)) {
            if (id == null) {
                throw new IllegalArgumentException("ID is required for reply type.");
            }
            model.addAttribute("id", id);
        } else if ("original".equals(type)) {
            id = null; // original의 경우 ID는 필요 없음
        } else {
            throw new IllegalArgumentException("Invalid type parameter: " + type);
        }

        // 글쓰기 로직 수행
        boardService.addContents(vo, type, id, authUser.getId());

        // 완료 후 리다이렉트
        return "redirect:/board";
    }

    @RequestMapping(value = "/update/{id}",method = RequestMethod.GET)
    public String update(@PathVariable Long id, HttpSession session,Model model){

        BoardVo vo = boardService.getContents(id);
        model.addAttribute("vo",vo);

        return "board/modify";
    }

    @RequestMapping(value = "/update/{id}",method = RequestMethod.POST)
    public String update(@PathVariable Long id, BoardVo vo){

        boardService.updateContents(vo);
        return "redirect:/board";
    }

    @RequestMapping(value = "/view/{id}",method = RequestMethod.GET)
    public String view(@PathVariable Long id, Model model){
        BoardVo vo = boardService.getContents(id);

        model.addAttribute("vo",vo);
        return "board/view";
    }

    @RequestMapping(value="/delete/{id}", method=RequestMethod.GET)
    public String delete(@PathVariable(name = "id") Long id) {
        return "guestbook/delete";
    }

    @RequestMapping(value = "/delete/{id}",method = RequestMethod.POST)
    public String delete(@AuthUser UserVo authUser, @PathVariable(name = "id") Long id) {
        boardService.deleteContents(id,authUser.getId());
        return "redirect:/board";
    }
}
