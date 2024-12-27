package mysite.controller.action.board;

import mysite.controller.ActionServlet;
import mysite.dao.BoardDao;
import mysite.dao.UserDao;
import mysite.vo.BoardVo;
import mysite.vo.UserVo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AddAction implements ActionServlet.Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        // Access Control
        if (session == null) {
            response.sendRedirect(request.getContextPath());
            return;
        }
        UserVo authUser = (UserVo) session.getAttribute("authUser");
        if (authUser == null) {
            response.sendRedirect(request.getContextPath());
            return;
        }

        // 요청 파라미터 가져오기
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        String type = request.getParameter("type"); // "original" or "reply"
        System.out.println("parameter -> " + type);

        BoardDao dao = new BoardDao();
        // insert함수 호출
        if ("reply".equals(type)) {
            // 답글 처리
            int originalId = Integer.parseInt(request.getParameter("id")); // 원본 글 ID

            // 원본 글 정보 조회
            BoardVo originalPost = dao.findById(originalId);

            if (originalPost != null) {
                // 답글 데이터 설정
                BoardVo replyPost = new BoardVo();
                replyPost.setTitle(title);
                replyPost.setContents(content);
                replyPost.setUserId(authUser.getId());
                replyPost.setGNo(originalPost.getGNo()); // 그룹 번호는 원본 글과 동일
                replyPost.setONo(originalPost.getONo()); // 답글의 순서
                replyPost.setDepth(originalPost.getDepth() + 1); // 답글의 깊이

                // 답글 삽입
                dao.insert(replyPost, true);
            }
        } else {
            // 일반 글 처리
            BoardVo post = new BoardVo();
            post.setTitle(title);
            post.setContents(content);
            post.setUserId(authUser.getId());

            dao.insert(post, false); // 새 글 삽입
        }

        // 글 목록으로 리디렉션
        response.sendRedirect(request.getContextPath() + "/board?page=1");
    }
}
