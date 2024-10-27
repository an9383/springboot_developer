package org.sb0907.springbootdeveloper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class TestController {
    @Autowired
    TestService testService;

    // 테이블의 모든 레코드를 조회
    @GetMapping("/test")
    public List<Member> getAllMembers() {
        List<Member> members = testService.getAllMembers();
        return members;
    }

    // 테이블에서 1개의 레코드를 조회
    @GetMapping("/test2")
    public Optional<Member> getMember() {
        Optional<Member> member = testService.getMember(5L);
        return member;
    }
}
