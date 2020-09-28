package com.kb.www.dao;

import com.kb.www.vo.ArticleVo;
import com.kb.www.vo.MemberHistoryVo;
import com.kb.www.vo.MemberVo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.kb.www.common.JdbcUtil.close;

public class BoardDao {
    private Connection con;

    private BoardDao() {

    }

    public static BoardDao getInstance() {
        return LazyHolder.INSTANCE;
    }

    private static class LazyHolder {
        private static final BoardDao INSTANCE = new BoardDao();
    }

    public void setConnection(Connection con) {
        this.con = con;
    }

    public ArrayList<ArticleVo> getArticleList() {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<ArticleVo> list = new ArrayList<>();
        try {
            pstmt = con.prepareStatement
                    ("select b.num" +
                            ", m.id" +
                            ", b.subject" +
                            ", b.content" +
                            ", b.hit" +
                            ", b.wdate" +
                            ", b.udate" +
                            ", b.ddate" +
                            " from board b" +
                            " inner join member m on b.mb_sq = m.sq");
            rs = pstmt.executeQuery();
            while(rs.next()) {
                ArticleVo vo = new ArticleVo();
                vo.setNum(rs.getInt("num"));
                vo.setSubject(rs.getString("subject"));
                vo.setContent(rs.getString("content"));
                vo.setHit(rs.getInt("hit"));
                vo.setWdate(rs.getString("wdate"));
                vo.setUdate(rs.getString("udate"));
                vo.setDdate(rs.getString("ddate"));
                vo.setId(rs.getString("id"));
                list.add(vo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            close(rs);
            close(pstmt);
        }
        return list;
    }

    public ArticleVo getArticle(int num) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArticleVo vo = null;
        try {
            pstmt = con.prepareStatement("select * from board where num=?");
            pstmt.setInt(1, num);
            rs = pstmt.executeQuery();
            while(rs.next()) {
                vo = new ArticleVo();
                vo.setNum(rs.getInt("num"));
                vo.setSubject(rs.getString("subject"));
                vo.setContent(rs.getString("content"));
                vo.setHit(rs.getInt("hit"));
                vo.setWdate(rs.getString("wdate"));
                vo.setUdate(rs.getString("udate"));
                vo.setDdate(rs.getString("ddate"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            close(rs);
            close(pstmt);
        }
        return vo;
    }

    public int insertArticle(ArticleVo vo) {
        PreparedStatement pstmt = null;
        int count = 0;
        try {
            pstmt = con.prepareStatement
                    ("insert into board(mb_sq, subject, content) value(?, ?, ?)");
            pstmt.setInt(1, vo.getMb_sq());
            pstmt.setString(2, vo.getSubject());
            pstmt.setString(3, vo.getContent());
            count = pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            close(pstmt);
        }
        return count;
    }

    public int deleteArticle(int num) {
        PreparedStatement pstmt = null;
        int count = 0;
        try {
            pstmt = con.prepareStatement("delete from board where num=?");
            pstmt.setInt(1, num);
            count = pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            close(pstmt);
        }
        return count;
    }

    public int updateArticle(ArticleVo vo) {
        PreparedStatement pstmt = null;
        int count = 0;
        try {
            pstmt = con.prepareStatement("update board set subject=?, content=?, udate=? where num=?");
            pstmt.setString(1, vo.getSubject());
            pstmt.setString(2, vo.getContent());
            pstmt.setString(3,
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            pstmt.setInt(4, vo.getNum());
            count = pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            close(pstmt);
        }
        return count;
    }

    public int updateHitCount(int num) {
        PreparedStatement pstmt = null;
        int count = 0;
        try {
            pstmt = con.prepareStatement("update board set hit=hit+1 where num=?");
            pstmt.setInt(1, num);
            count = pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            close(pstmt);
        }
        return count;
    }

    public int insertMember(MemberVo vo) {
        PreparedStatement pstmt = null;
        int count = 0;
        try {
            pstmt = con.prepareStatement
                    ("insert into member(id, pwd) value(?, ?)");
            pstmt.setString(1, vo.getId());
            pstmt.setString(2, vo.getPwd());
            count = pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            close(pstmt);
        }
        return count;
    }

    public int getMemberSequence(String id) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int sq = 0;
        try {
            pstmt = con.prepareStatement("select sq from member where id=?");
            pstmt.setString(1, id);
            rs = pstmt.executeQuery();
            while(rs.next()) {
                sq = rs.getInt("sq");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            close(rs);
            close(pstmt);
        }
        return sq;
    }

    public int insertMemberHistory(MemberHistoryVo vo) {
        PreparedStatement pstmt = null;
        int count = 0;
        try {
            pstmt = con.prepareStatement
                    ("insert into member_history(mb_sq, evt_type) value(?, ?)");
            pstmt.setInt(1, vo.getMb_sq());
            pstmt.setInt(2, vo.getEvt_type());
            count = pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            close(pstmt);
        }
        return count;
    }

    public MemberVo getMember(String id) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        MemberVo vo = null;
        try {
            pstmt = con.prepareStatement
                    ("select sq, id, pwd from member where binary(id)=?");
            pstmt.setString(1, id);
            rs = pstmt.executeQuery();
            while(rs.next()) {
                vo = new MemberVo();
                vo.setSq(rs.getInt("sq"));
                vo.setId(rs.getString("id"));
                vo.setPwd(rs.getString("pwd"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            close(rs);
            close(pstmt);
        }
        return vo;
    }

    public int updateLoginState(MemberVo vo) {
        PreparedStatement pstmt = null;
        int count = 0;
        try {
            pstmt = con.prepareStatement
                    ("update member set lgn_fl=? where sq=?");
            pstmt.setBoolean(1, vo.isLgn_fl());
            pstmt.setInt(2, vo.getSq());
            count = pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            close(pstmt);
        }
        return count;
    }
}









