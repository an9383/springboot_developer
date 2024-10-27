package org.sb0907.springbootdeveloper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

// @SpringBootTest와 @DataJpaTest의 차이점
// 1. @SpringBootTest: Spring 컨테이너의 모든 빈을 등록함
// 2. @DataJpaTest: JPA(데이터베이스)와 관련된 빈만 등록함

// @SpringBootTest
@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.yml")
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    // 1. R(Read, 전체 데이터 조회)
    @DisplayName("전체 데이터 조회")
    @Sql("/insert-members.sql")
    @Test
    public void getAllMembers() {
        // when
        List<Member> members = memberRepository.findAll();
        // then
        assertThat(members.size()).isEqualTo(3);
        // print
        for(int i=0; i<members.size(); i++) {
            System.out.println(i + "번째 멤버: " + members.get(i));
        }
    }

    // 2. R(Read, id로 데이터 1건 조회)
    @DisplayName("id로 데이터 1건 조회")
    @Sql("/insert-members.sql")
    @Test
    public void getMemberById() {
        // when
        Optional<Member> member = memberRepository.findById(2L);
        // then
        assertThat(member.get().getName()).isEqualTo("B");
    }

    // 3. R(Read, name으로 데이터 1건 조회)
    @DisplayName("name으로 데이터 1건 조회")
    @Sql("/insert-members.sql")
    @Test
    public void getMemberByName() {
        // when
        Optional<Member> member = memberRepository.findByName("C");
        // then
        assertThat(member.get().getId()).isEqualTo(3L);
    }

    // 4. C(Create, 데이터 1건 추가)
    @DisplayName("데이터 1건 조회")
    @Test
    public void saveMember() {
        // given
        Member member = new Member(1L, "A");
        // when
        memberRepository.save(member);
        // then
        assertThat(memberRepository.findById(1L).get().getName()).isEqualTo("A");
    }

    // 5. C(Create, 데이터 여러 건 추가)
    @DisplayName("데이터 여러 건 추가")
    @Test
    public void saveMembers() {
        // given
        List<Member> members = List.of(new Member(2L, "B"), new Member(3L, "C"));
        // when
        memberRepository.saveAll(members)  ;
        // then
        assertThat(memberRepository.findAll().size()).isEqualTo(2);
    }

    // 6. D(Delete, id로 데이터 1건 삭제)
    @DisplayName("id로 데이터 1건 삭제")
    @Sql("/insert-members.sql")
    @Test
    public void deleteMemberById() {
        // when
        memberRepository.deleteById(2L);
        // then
        assertThat(memberRepository.findById(2L).isEmpty()).isTrue();
    }

    // 7. D(Delete, name으로 1건 삭제)
    @DisplayName("name으로 1건 삭제")
    @Sql("/insert-members.sql")
    @Test
    public void deleteMemberByName() {
        // when
        memberRepository.deleteByName("C");
        // then
        assertThat(memberRepository.findByName("C").isEmpty()).isTrue();
    }

    // 8. D(Delete, 모든 데이터 삭제)
    @DisplayName("모든 데이터 삭제")
    @Sql("/insert-members.sql")
    @Test
    public void deleteAll() {
        // when
        memberRepository.deleteAll();
        // then
        assertThat(memberRepository.findAll().size()).isEqualTo(0);
    }

    // 9. U(Update, id로 name 수정)
    @DisplayName("id로 name 수정")
    @Sql("/insert-members.sql")
    @Test
    @Transactional
    @Commit
    public void update() {
        // given
        //Optional<Member> member = memberRepository.findById(2L);
        Member member = memberRepository.findById(2L).get();
        // when
        member.setName("BB");
        // then
        assertThat(memberRepository.findById(2L).get().getName()).isEqualTo("BB");
    }

}