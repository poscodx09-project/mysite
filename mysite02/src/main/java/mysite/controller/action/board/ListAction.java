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
            response.sendRedirect(request.getContextPath());
            return;
        }
        UserVo authUser = (UserVo)session.getAttribute("authUser");
        if(authUser == null) {
            response.sendRedirect(request.getContextPath());
            return;
        }
        ///////////////////////////////////////////////////////////
        BoardDao dao = new BoardDao();

        // 페이징 처리 변수
        String pageParam = request.getParameter("page");
        int currentPage = (pageParam == null || pageParam.isEmpty()) ? 1 : Integer.parseInt(pageParam);
        int pageSize = 5; // 한 페이지당 표시할 게시물 수
        int blockSize = 3; // 한 블록에 표시할 페이지 수

        // 검색 키워드 처리
        String keyword = request.getParameter("kwd"); // 검색 키워드
        if (keyword != null && !keyword.trim().isEmpty()) {
            keyword = keyword.trim();
        } else {
            keyword = null; // 검색 키워드가 없으면 null로 처리
        }

        // 총 게시물 수 계산 (검색 키워드 포함)
        int totalCount = (keyword != null) ? dao.findAllWithPagingAndKwd(keyword,currentPage,pageSize).size() : dao.getTotalCount();
        int totalPages = (int) Math.ceil((double) totalCount / pageSize);

        // 현재 블록 계산
        int currentBlock = (int) Math.ceil((double) currentPage / blockSize);
        int beginPage = (currentBlock - 1) * blockSize + 1;
        int endPage = Math.min(beginPage + blockSize - 1, totalPages);

        // 게시물 리스트 조회
        List<BoardVo> boardList;
        if (keyword != null) {
            boardList = dao.findAllWithPagingAndKwd(keyword, currentPage, pageSize);
        } else {
            boardList = dao.findAllWithPaging(currentPage, pageSize);
        }

        // JSP에 데이터 전달
        request.setAttribute("boardList", boardList);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("beginPage", beginPage);
        request.setAttribute("endPage", endPage);
        request.setAttribute("kwd", keyword);

        // JSP로 포워딩
        request.getRequestDispatcher("/WEB-INF/views/board/list.jsp").forward(request, response);
    }

}
