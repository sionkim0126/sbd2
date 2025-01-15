package sbd.example.repository;

import sbd.example.domain.Member;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.sql.DriverManager.getConnection;

public class JdbcMemberRepository implements MemberRepository {

    private final DataSource dataSource;

    public JdbcMemberRepository(DataSource dataSource){
        this.dataSource = dataSource;
    }

    @Override
    public Member save(Member member) {
        String sql = "insert into member(name) values(?)";

        Connection con = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;

        try{
            con = getConnection();
            psmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            psmt.setString(1, member.getName());

            psmt.executeUpdate();

            rs = psmt.getGeneratedKeys();

            if(rs.next()){
                member.setId(rs.getLong(1));
            }else {
                throw new SQLException("id 조회실패!");
            }
        return member;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(con, psmt, rs);

        }
    }

    @Override
    public Optional<Member> findById(long id) {

        String sql = "select * from member where id = ?";

        Connection con = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;

        try{
            con = getConnection();
            psmt = con.prepareStatement(sql);
            psmt.setLong(1, id);

            rs = psmt.executeQuery();
            if(rs.next()){
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                return Optional.of(member);
            }else {
                return Optional.empty();
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }finally {
            close(con, psmt, rs);
        }
    }

    @Override
    public Optional<Member> findByName(String name) {

        String sql = "select * from member where name = ?";

        Connection con = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;

        try{
            con = getConnection();
            psmt = con.prepareStatement(sql);
            psmt.setString(1, name);

            rs = psmt.executeQuery();
            if(rs.next()){
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                return Optional.of(member);
            }else {
                return Optional.empty();
            }
        }catch (SQLException e){
            throw new IllegalStateException(e);
        }finally {
            close(con, psmt, rs);
        }
    }

    @Override
    public List<Member> findAll() {

        String sql = "select * from member";

        Connection con = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;

        try{
            con = getConnection();
            psmt = con.prepareStatement(sql);

            rs = psmt.executeQuery();

            List<Member>members = new ArrayList<>();
            while (rs.next()){
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                members.add(member);
            }
            return members;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(con, psmt, rs);
        }
    }
    private Connection getConnection() {
        return DataSourceUtils.getConnection(dataSource);
    }

    private void close(Connection conn, PreparedStatement psmt, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (psmt != null) {
                psmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn != null) {
                close(conn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void close(Connection conn) throws SQLException {
        DataSourceUtils.releaseConnection(conn, dataSource);
    }
}
