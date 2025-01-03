package mysite.repository;

import mysite.vo.BoardVo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class BoardRepository {

    private final SqlSession sqlSession;

    @Autowired
    public BoardRepository(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    // 게시물 추가
    public void insert(BoardVo vo, boolean isReply) {
        if (!isReply) {
            // 1. 새 g_no 계산
            int newGNo = sqlSession.selectOne("board.selectMaxGNo");
            vo.setGNo(newGNo);

            // 2. 새 게시물 삽입
            sqlSession.insert("board.insertBoard", vo);
        } else {
            insertReply(vo);
        }
    }

    // 답글 삽입
    private void insertReply(BoardVo vo) {
        // 1. 답글 순서 조정
        sqlSession.update("board.updateOrderForReply", vo);

        // 2. 답글 삽입
        vo.setONo(vo.getONo() + 1); // 답글의 o_no 증가
        vo.setDepth(vo.getDepth() + 1); // 답글의 depth 증가
        sqlSession.insert("board.insertReply", vo);
    }

    // 특정 게시물 조회
    public BoardVo findById(Long id) {
        return sqlSession.selectOne("board.findById", id);
    }

    // 게시물 수정
    public void update(BoardVo vo) {
        sqlSession.update("board.updateBoard", vo);
    }

    // 게시물 삭제
    public void delete(Long id) {
        sqlSession.delete("board.deleteBoard", id);
    }

    // 게시물 리스트 조회
    public List<BoardVo> findAllWithPaging(int page, int pageSize) {
        Map<String, Object> params = new HashMap<>();
        params.put("offset", (page - 1) * pageSize);
        params.put("pageSize", pageSize);
        return sqlSession.selectList("board.findAllWithPaging", params);
    }

    // 검색어 포함 게시물 조회
    public List<BoardVo> findAllWithPagingAndKwd(String kwd, int page, int pageSize) {
        Map<String, Object> params = new HashMap<>();
        params.put("kwd", kwd);
        params.put("offset", (page - 1) * pageSize);
        params.put("pageSize", pageSize);
        return sqlSession.selectList("board.findAllWithPagingAndKwd", params);
    }

    // 게시물 총 개수 조회
    public int getTotalCount() {
        return sqlSession.selectOne("board.getTotalCount");
    }

    // 조회수 증가
    public void incrementHit(Long id) {
        sqlSession.update("board.incrementHit", id);
    }
}
