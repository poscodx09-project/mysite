package mysite.service;

import mysite.repository.BoardRepository;
import mysite.vo.BoardVo;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BoardService {

    private final BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository){
        this.boardRepository = boardRepository;
    }

    public void addContents(BoardVo vo,String type,Long id,Long userId) {

        if ("reply".equals(type)) {
            // 원본 글 정보 조회
            BoardVo originalPost = boardRepository.findById(id);
            System.out.println("sss" + originalPost.getId());
            System.out.println("111 testtest => "+ originalPost.getONo());
            if (originalPost != null) {
                // 답글 데이터 설정
                BoardVo replyPost = new BoardVo();
                replyPost.setTitle(vo.getTitle());
                replyPost.setContents(vo.getContents());
                replyPost.setUserId(userId);
                replyPost.setGNo(originalPost.getGNo()); // 그룹 번호는 원본 글과 동일
                replyPost.setONo(originalPost.getONo()); // 답글의 순서
                System.out.println("testtest => "+ originalPost.getONo());
                replyPost.setDepth(originalPost.getDepth() + 1); // 답글의 깊이

                // 답글 삽입
                boardRepository.insert(replyPost, true);
            }
        } else {
            // 일반 글 처리
            BoardVo post = new BoardVo();
            post.setTitle(vo.getTitle());
            post.setContents(vo.getContents());
            post.setUserId(userId);

            boardRepository.insert(post, false); // 새 글 삽입
        }

    }

    public BoardVo getContents(Long id) {
        boardRepository.incrementHit(id);
        return boardRepository.findById(id);
    }


    public void updateContents(BoardVo vo) {
        boardRepository.update(vo);
    }

    public void deleteContents(Long id, Long userId) {
        boardRepository.delete(id);
    }

    public Map<String, Object> getContentsList(int currentPage, String keyword) {
        List<BoardVo> list = null;

        // view의 pagination를 위한 데이터 값 계산
        int pageSize = 5; // 한 페이지당 표시할 게시물 수
        int blockSize = 3; // 한 블록에 표시할 페이지 수

        if (keyword != null && !keyword.trim().isEmpty()) {
            keyword = keyword.trim();
        } else {
            keyword = null; // 검색 키워드가 없으면 null로 처리
        }

        // 총 게시물 수 계산 (검색 키워드 포함)
        int totalCount = (keyword != null) ? boardRepository.findAllWithPagingAndKwd(keyword,currentPage,pageSize).size() : boardRepository.getTotalCount();
        int totalPages = (int) Math.ceil((double) totalCount / pageSize);

        // 현재 블록 계산
        int currentBlock = (int) Math.ceil((double) currentPage / blockSize);
        int beginPage = (currentBlock - 1) * blockSize + 1;
        int endPage = Math.min(beginPage + blockSize - 1, totalPages);

        if (keyword != null) {
            list = boardRepository.findAllWithPagingAndKwd(keyword, currentPage, pageSize);
        } else {
            list = boardRepository.findAllWithPaging(currentPage, pageSize);
        }

        Map<String, Object> res = new HashMap<String, Object>();
        res.put("boardList",list);
        res.put("beginPage", beginPage);
        res.put("currentPage",currentPage);
        res.put("endPage",endPage);
        res.put("totalPages",totalPages);

        return res;
    }
}
