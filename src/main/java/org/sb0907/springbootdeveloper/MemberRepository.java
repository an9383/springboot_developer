package org.sb0907.springbootdeveloper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    // name으로 데이터 조회
    Optional<Member> findByName(String name);

    // name으로 데이터 삭제
    void deleteByName(String name);

}
