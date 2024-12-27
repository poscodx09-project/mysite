package mysite.repository;

import mysite.vo.BoardVo;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BoardRepository {

    private Connection getConnection() throws SQLException {
        Connection conn = null;
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            String url = "jdbc:mariadb://192.168.0.17:3306/webdb";
            conn = DriverManager.getConnection(url, "webdb", "webdb");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver loading failed: " + e);
        }
        return conn;
    }

    // Create: 게시물 추가
    public void insert(BoardVo vo, boolean isReply) {
        if (!isReply) {
            String selectMaxGNoSql = "SELECT IFNULL(MAX(g_no), 0) + 1 FROM board";
            String insertSql = "INSERT INTO board (title, g_no, o_no, depth, reg_date, hit, contents, user_id) " +
                    "VALUES (?, ?, 0, 0, NOW(), 0, ?, ?)";

            try (Connection conn = getConnection()) {
                // 1. 최대 g_no 조회
                int newGNo = 1; // 기본값
                try (PreparedStatement pstmt = conn.prepareStatement(selectMaxGNoSql);
                     ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        newGNo = rs.getInt(1);
                    }
                }

                // 2. 데이터 삽입
                try (PreparedStatement pstmt = conn.prepareStatement(insertSql)) {
                    pstmt.setString(1, vo.getTitle());
                    pstmt.setInt(2, newGNo); // 새로운 g_no
                    pstmt.setString(3, vo.getContents());
                    pstmt.setLong(4, vo.getUserId());

                    pstmt.executeUpdate();
                }
            } catch (SQLException e) {
                System.out.println("Insert failed (new post): " + e);
            }
        } else {
            // 답글 삽입 로직 그대로 유지
            insertReply(vo);
        }
    }

    private void insertReply(BoardVo vo) {
        String updateOrderSql = "UPDATE board SET o_no = o_no + 1 WHERE g_no = ? AND o_no > ?";
        String insertReplySql = "INSERT INTO board (title, g_no, o_no, depth, reg_date, hit, contents, user_id) " +
                "VALUES (?, ?, ?, ?, NOW(), 0, ?, ?)";

        try (Connection conn = getConnection()) {
            conn.setAutoCommit(false);

            // 1. o_no 조정
            try (PreparedStatement pstmt = conn.prepareStatement(updateOrderSql)) {
                pstmt.setInt(1, vo.getGNo());
                pstmt.setInt(2, vo.getONo());
                pstmt.executeUpdate();
            }

            // 2. 답글 삽입
            try (PreparedStatement pstmt = conn.prepareStatement(insertReplySql)) {
                pstmt.setString(1, vo.getTitle());
                pstmt.setInt(2, vo.getGNo());
                pstmt.setInt(3, vo.getONo() + 1);
                pstmt.setInt(4, vo.getDepth() + 1);
                pstmt.setString(5, vo.getContents());
                pstmt.setLong(6, vo.getUserId());

                pstmt.executeUpdate();
            }

            conn.commit();
        } catch (SQLException e) {
            System.out.println("Insert failed (reply): " + e);
        }
    }



    // Read: 특정 게시물 조회
    public BoardVo findById(Long id) {
        BoardVo vo = null;
        String sql = "SELECT id, title, g_no, o_no, depth, reg_date, hit, contents, user_id FROM board WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    vo = new BoardVo();
                    vo.setId(rs.getLong("id"));
                    vo.setTitle(rs.getString("title"));
                    vo.setGNo(rs.getInt("g_no"));
                    vo.setONo(rs.getInt("o_no"));
                    vo.setDepth(rs.getInt("depth"));
                    Timestamp timestamp = rs.getTimestamp("reg_date");
                    if (timestamp != null) {
                        LocalDateTime localDateTime = timestamp.toLocalDateTime();
                        // 원하는 형식으로 변환
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                        String formattedDate = localDateTime.format(formatter);
                        vo.setRegDate(formattedDate); // regDate를 String으로 변경한 경우
                    }
                    vo.setHit(rs.getInt("hit"));
                    vo.setContents(rs.getString("contents"));
                    vo.setUserId(rs.getLong("user_id"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Find by ID failed: " + e);
        }
        return vo;
    }

    // Update: 게시물 수정
    public void update(BoardVo vo) {
        String sql = "UPDATE board SET title = ?, contents = ? WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, vo.getTitle());
            pstmt.setString(2, vo.getContents());
            pstmt.setLong(3, vo.getId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Update failed: " + e);
        }
    }

    // Delete: 게시물 삭제
    public void delete(Long id) {
        String sql = "DELETE FROM board WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, id);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Delete failed: " + e);
        }
    }

    // Read: 게시물 리스트 조회 (페이징 포함)
    public List<BoardVo> findAllWithPaging(int page, int pageSize) {
        List<BoardVo> list = new ArrayList<>();
        String sql = "SELECT b.id, b.title, b.g_no, b.o_no, b.depth, b.reg_date, b.hit, b.user_id, u.name " +
                "FROM board b " +
                "JOIN `user` u ON b.user_id = u.id " +
                "ORDER BY g_no DESC, o_no ASC " +
                "LIMIT ?, ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            int offset = (page - 1) * pageSize;
            pstmt.setInt(1, offset);
            pstmt.setInt(2, pageSize);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    BoardVo vo = new BoardVo();
                    vo.setId(rs.getLong("id"));
                    vo.setWriter(rs.getString("name"));
                    vo.setTitle(rs.getString("title"));
                    vo.setGNo(rs.getInt("g_no"));
                    vo.setONo(rs.getInt("o_no"));
                    vo.setDepth(rs.getInt("depth"));
                    Timestamp timestamp = rs.getTimestamp("reg_date");
                    if (timestamp != null) {
                        LocalDateTime localDateTime = timestamp.toLocalDateTime();
                        // 원하는 형식으로 변환
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                        String formattedDate = localDateTime.format(formatter);
                        vo.setRegDate(formattedDate); // regDate를 String으로 변경한 경우
                    }
                    vo.setHit(rs.getInt("hit"));
                    vo.setUserId(rs.getLong("user_id"));
                    list.add(vo);
                }
            }
        } catch (SQLException e) {
            System.out.println("Find all with paging failed: " + e);
        }
        return list;
    }

    public List<BoardVo> findAllWithPagingAndKwd(String kwd, int page, int pageSize) {
        List<BoardVo> list = new ArrayList<>();
        String sql = "SELECT b.id, b.title, b.g_no, b.o_no, b.depth, b.reg_date, b.hit, b.user_id, u.name " +
                "FROM board b " +
                "JOIN `user` u ON b.user_id = u.id " +
                "WHERE b.title like ? OR b.contents like ? "+
                "ORDER BY g_no DESC, o_no ASC " +
                "LIMIT ?, ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            int offset = (page - 1) * pageSize;
            pstmt.setString(1, kwd);
            pstmt.setString(2, kwd);
            pstmt.setInt(3, offset);
            pstmt.setInt(4, pageSize);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    BoardVo vo = new BoardVo();
                    vo.setId(rs.getLong("id"));
                    vo.setWriter(rs.getString("name"));
                    vo.setTitle(rs.getString("title"));
                    vo.setGNo(rs.getInt("g_no"));
                    vo.setONo(rs.getInt("o_no"));
                    vo.setDepth(rs.getInt("depth"));
                    Timestamp timestamp = rs.getTimestamp("reg_date");
                    if (timestamp != null) {
                        LocalDateTime localDateTime = timestamp.toLocalDateTime();
                        // 원하는 형식으로 변환
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                        String formattedDate = localDateTime.format(formatter);
                        vo.setRegDate(formattedDate); // regDate를 String으로 변경한 경우
                    }
                    vo.setHit(rs.getInt("hit"));
                    vo.setUserId(rs.getLong("user_id"));
                    list.add(vo);
                }
            }
        } catch (SQLException e) {
            System.out.println("Find all with paging failed: " + e);
        }
        return list;
    }

    // Total Count: 게시물 총 개수 조회
    public int getTotalCount() {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM board";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Get total count failed: " + e);
        }
        return count;
    }

    public void incrementHit(int id) {
        String sql = "UPDATE board SET hit = hit + 1 WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Increment hit failed: " + e);
        }
    }

}

