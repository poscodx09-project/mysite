package mysite.repository;

import mysite.vo.GuestbookVo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class GuestBookRepository {
    private SqlSession sqlSession;

    @Autowired
    private DataSource dataSource;

    public GuestBookRepository(SqlSession sqlSession){
        this.sqlSession = sqlSession;
    }

    public List<GuestbookVo> findAll() {

        List<GuestbookVo> result = sqlSession.selectList("guestbook.findAll");
//        List<GuestbookVo> result = new ArrayList<>();
//
//        try (
//                Connection conn = dataSource.getConnection();
//                PreparedStatement pstmt = conn.prepareStatement("select id, name, contents, date_format(reg_date, '%Y-%m-%d %h:%i:%s') from guestbook order by reg_date desc");
//                ResultSet rs = pstmt.executeQuery();
//        ) {
//            while(rs.next()) {
//                Long id = rs.getLong(1);
//                String name = rs.getString(2);
//                String contents = rs.getString(3);
//                String regDate = rs.getString(4);
//
//                GuestbookVo vo = new GuestbookVo();
//                vo.setId(id);
//                vo.setName(name);
//                vo.setContents(contents);
//                vo.setRegDate(regDate);
//
//                result.add(vo);
//            }
//        } catch (SQLException e) {
//            System.out.println("error:" + e);
//        }

        return result;
    }

    public int insert(GuestbookVo vo) {
        return sqlSession.insert("guestbook.insert", vo);

//        int count = 0;
//
//        try (
//                Connection conn = dataSource.getConnection();
//                PreparedStatement pstmt = conn.prepareStatement("insert into guestbook values(null, ?, ?, ?, now())");
//        ) {
//            pstmt.setString(1, vo.getName());
//            pstmt.setString(2, vo.getPassword());
//            pstmt.setString(3, vo.getContents());
//
//            count = pstmt.executeUpdate();
//        } catch (SQLException e) {
//            System.out.println("error:" + e);
//        }
//
//        return count;
    }

    public int deleteByIdAndPassword(Long id, String password) {
        Map<String, Object> map = Map.of("id",id,"password",password);
        return sqlSession.delete("guestbook.deleteByIdAndPassword",map);
//        int count = 0;
//
//        try (
//                Connection conn = dataSource.getConnection();
//                PreparedStatement pstmt = conn.prepareStatement("delete from guestbook where id=? and password=?");
//        ) {
//            pstmt.setLong(1, id);
//            pstmt.setString(2, password);
//
//            count = pstmt.executeUpdate();
//        } catch (SQLException e) {
//            System.out.println("error:" + e);
//        }
//
//        return count;
    }

//    private Connection getConnection() throws SQLException{
//        Connection conn = null;
//
//        try {
//            Class.forName("org.mariadb.jdbc.Driver");
//
//            String url = "jdbc:mariadb://192.168.0.17:3306/webdb";
//            conn = DriverManager.getConnection(url, "webdb", "webdb");
//        } catch (ClassNotFoundException e) {
//            System.out.println("드라이버 로딩 실패:" + e);
//        }
//
//        return conn;
//    }
}
