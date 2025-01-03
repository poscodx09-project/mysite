package mysite.repository;

import mysite.vo.UserVo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Map;

@Repository
public class UserRepository {

    private SqlSession sqlSession;

    @Autowired
    private DataSource dataSource;
    public UserRepository(SqlSession sqlSession){
        this.sqlSession = sqlSession;
    }

    public int insert(UserVo vo) {
        return sqlSession.insert("user.insert",vo);
//        int count = 0;
//
//        try (
//                Connection conn = dataSource.getConnection();
//                PreparedStatement pstmt = conn.prepareStatement("insert into user values(null, ?, ?, ?, ?, now(),'USER')");
//        ) {
//            pstmt.setString(1, vo.getName());
//            pstmt.setString(2, vo.getEmail());
//            pstmt.setString(3, vo.getPassword());
//            pstmt.setString(4, vo.getGender());
//
//            count = pstmt.executeUpdate();
//        } catch (SQLException e) {
//            System.out.println("error:" + e);
//        }
//
//        return count;
    }

    public UserVo findByEmailAndPassword(String email, String password) {
        Map<String,Object> map = Map.of("email",email,"password",password);
        return sqlSession.selectOne("user.findByEmailAndPassword",map);
//        UserVo userVo = null;
//
//        try (
//                Connection conn = dataSource.getConnection();
//                PreparedStatement pstmt = conn.prepareStatement("select id, name, role from user where email=? and password=?");
//        ) {
//            pstmt.setString(1, email);
//            pstmt.setString(2, password);
//
//            ResultSet rs = pstmt.executeQuery();
//            if(rs.next()) {
//                Long id = rs.getLong(1);
//                String name = rs.getString(2);
//                String role = rs.getString(3);
//
//                userVo = new UserVo();
//                userVo.setId(id);
//                userVo.setName(name);
//                userVo.setRole(role);
//            }
//
//            rs.close();
//        } catch (SQLException e) {
//            System.out.println("error:" + e);
//        }
//
//        return userVo;
    }

    public UserVo findById(Long userId) {
//        Map<String,Object> map = Map.of("id",userId);
        return sqlSession.selectOne("user.findById",userId);

//        UserVo result = null;
//
//        try (
//                Connection conn = dataSource.getConnection();
//                PreparedStatement pstmt = conn.prepareStatement("select id, name, email, gender from user where id = ?");
//        ) {
//
//            pstmt.setLong(1, userId);
//
//            ResultSet rs = pstmt.executeQuery();
//            if(rs.next()) {
//                Long id = rs.getLong(1);
//                String name = rs.getString(2);
//                String email = rs.getString(3);
//                String gender = rs.getString(4);
//
//                result = new UserVo();
//                result.setId(id);
//                result.setName(name);
//                result.setEmail(email);
//                result.setGender(gender);
//            }
//            rs.close();
//        } catch (SQLException e) {
//            System.out.println("Error:" + e);
//        }
//
//        return result;
    }

    public int update(UserVo vo) {
        return sqlSession.update("user.update", vo);

//        int result = 0;
//
//        try (
//                Connection conn = dataSource.getConnection();
//                PreparedStatement pstmt1 = conn.prepareStatement("update user set name=?, gender=? where id=?");
//                PreparedStatement pstmt2 = conn.prepareStatement("update user set name=?, password=?, gender=? where id=?");
//        ) {
//            if("".equals(vo.getPassword())) {
//                pstmt1.setString(1, vo.getName());
//                pstmt1.setString(2, vo.getGender());
//                pstmt1.setLong(3, vo.getId());
//                result = pstmt1.executeUpdate();
//            } else {
//                pstmt2.setString(1, vo.getName());
//                pstmt2.setString(2, vo.getPassword());
//                pstmt2.setString(3, vo.getGender());
//                pstmt2.setLong(4, vo.getId());
//                result = pstmt2.executeUpdate();
//            }
//        } catch (SQLException e) {
//            System.out.println("Error:" + e);
//        }
//
//        return result;
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
