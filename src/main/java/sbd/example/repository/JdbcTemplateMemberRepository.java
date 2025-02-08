package sbd.example.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import sbd.example.domain.Member;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

// JdbcTemplate을 사용해 데이터베이스와 연동하는 MemberRepository 구현체
public class JdbcTemplateMemberRepository implements MemberRepository {

    private final JdbcTemplate jdbcTemplate;

    // DataSource를 통해 JdbcTemplate 초기화
    public JdbcTemplateMemberRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // 회원 정보 저장 메서드
    @Override
    public Member save(Member member) {
        // SimpleJdbcInsert를 사용해 자동 생성 키를 포함한 INSERT 처리
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("member").usingGeneratedKeyColumns("id"); // 테이블 이름과 자동 생성 키 컬럼 지정

        // 삽입할 데이터 매핑
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", member.getName());

        // INSERT 실행 후 자동 생성된 ID 반환
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        member.setId(key.longValue()); // 반환된 키를 Member 객체에 설정
        return member; // 저장된 Member 객체 반환
    }

    // ID로 회원을 조회하는 메서드
    @Override
    public Optional<Member> findById(long id) {
        // SQL 실행 후 결과를 MemberRowMapper로 변환
        List<Member> result = jdbcTemplate.query("select * from MEMBER where id = ?", MemberRowMapper(), id);
        return result.stream().findAny(); // 결과 중 첫 번째 값을 Optional로 반환
    }

    // 이름으로 회원을 조회하는 메서드
    @Override
    public Optional<Member> findByName(String name) {
        // SQL 실행 후 결과를 MemberRowMapper로 변환
        List<Member> result = jdbcTemplate.query("select * from MEMBER where name = ?", MemberRowMapper(), name);
        return result.stream().findAny(); // 결과 중 첫 번째 값을 Optional로 반환
    }

    // 모든 회원을 조회하는 메서드
    @Override
    public List<Member> findAll() {
        // SQL 실행 후 결과를 MemberRowMapper로 변환하여 리스트 반환
        return jdbcTemplate.query("select * from MEMBER", MemberRowMapper());
    }

    // RowMapper를 사용해 ResultSet을 Member 객체로 변환
    private RowMapper<Member> MemberRowMapper() {
        return (rs, rowNum) -> {
            // ResultSet에서 데이터를 읽어 Member 객체 생성
            Member member = new Member();
            member.setId(rs.getLong("id")); // id 컬럼 값을 Member 객체에 설정
            member.setName(rs.getString("name")); // name 컬럼 값을 Member 객체에 설정
            return member; // 변환된 Member 객체 반환
        };
    }
}
