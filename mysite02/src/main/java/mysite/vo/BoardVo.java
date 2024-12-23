package mysite.vo;


import java.time.LocalDateTime;

public class BoardVo {
    private Long id; // 게시물 ID (Primary Key)
    private String title; // 게시물 제목

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    private Integer gNo; // 그룹 번호
    private Integer oNo; // 순서 번호
    private int depth; // 계층 깊이
    private String regDate; // 작성일
    private Integer hit; // 조회수
    private String contents; // 게시물 내용
    private Long userId; // 작성자 ID (Foreign Key)

    private String writer;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getGNo() {
        return gNo;
    }

    public void setGNo(Integer gNo) {
        this.gNo = gNo;
    }

    public Integer getONo() {
        return oNo;
    }

    public void setONo(Integer oNo) {
        this.oNo = oNo;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public Integer getHit() {
        return hit;
    }

    public void setHit(Integer hit) {
        this.hit = hit;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    // toString() for debugging and logging
    @Override
    public String toString() {
        return "BoardVo{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", gNo=" + gNo +
                ", oNo=" + oNo +
                ", depth=" + depth +
                ", regDate=" + regDate +
                ", hit=" + hit +
                ", contents='" + contents + '\'' +
                ", userId=" + userId +
                '}';
    }
}

