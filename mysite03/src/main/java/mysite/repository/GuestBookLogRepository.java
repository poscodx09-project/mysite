package mysite.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
@Repository
public class GuestBookLogRepository {
    private SqlSession sqlSession;

    public GuestBookLogRepository(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public int insert() {
        return sqlSession.update("guestbook-log.insert");
    }

    public int update() {
        return sqlSession.update("guestbook-log.update");
    }

    public int updateByRegDate(String regDate) {
        return sqlSession.update("guestbook-log.updateByRegDate", regDate);
    }
}
