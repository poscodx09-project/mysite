package mysite.controller.action.board;

import mysite.controller.ActionServlet;
import mysite.dao.BoardDao;
import mysite.dao.GuestbookDao;
import mysite.vo.BoardVo;
import mysite.vo.GuestbookVo;
import mysite.vo.UserVo;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class ListAction implements ActionServlet.Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();

        // Access Control
        if(session == null) {
            response.sendRedirect(request.getContextPath() + "/user?a=loginform");
            return;
        }
        UserVo authUser = (UserVo)session.getAttribute("authUser");
        if(authUser == null) {
            response.sendRedirect(request.getContextPath() + "/user?a=loginform");
            return;
        }
        ///////////////////////////////////////////////////////////

        BoardDao dao = new BoardDao();

        // 검색 처리
        if(request.getParameter("keyword") != ""){

        }





        ////////////////////////////////////////////////////////////
        // 페이징 처리
        int currentPage = Integer.parseInt(request.getParameter("page") == null ? "1" : request.getParameter("page"));
        int pageSize = 5; // 한 페이지당 표시할 게시물 수
        int blockSize = 3; // 한 블록에 표시할 페이지 수
        int totalCount = dao.getTotalCount(); // 총 게시물 수
        int totalPages = (int) Math.ceil((double) totalCount / pageSize);

        // 현재 블록 계산
        int currentBlock = (int) Math.ceil((double) currentPage / blockSize);
        int beginPage = (currentBlock - 1) * blockSize + 1;
        int endPage = Math.min(beginPage + blockSize - 1, totalPages);


        // 게시물 리스트 가져오기
        List<BoardVo> boardList = dao.findAllWithPaging(currentPage, pageSize);

        // JSP에 데이터 전달
        request.setAttribute("boardList", boardList);

        request.setAttribute("currentPage", currentPage);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("beginPage", beginPage);
        request.setAttribute("endPage", endPage);

        request.getRequestDispatcher("/WEB-INF/views/board/list.jsp").forward(request, response);
    }
}
