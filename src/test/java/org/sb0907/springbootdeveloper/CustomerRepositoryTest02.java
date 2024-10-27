package org.sb0907.springbootdeveloper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// 데이터베이스 CRUD를 하는 방법
// 2. @Query 에너테이션을 사용하는 방법
@DataJpaTest
@TestPropertySource(locations = "classpath:application.yml")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class CustomerRepositoryTest02 {
    @Autowired
    private CustomerRepository customerRepository;

    // 1. 추가 (Create)
    // 고객 데이터 추가
    @DisplayName("고객 데이터 추가")
    @Test
    public void testQueryInsert() {
        Customer customer = Customer.builder().name("김민종").age(31).phone("010-3333-3333").build();
        customerRepository.queryInsert(customer);
        System.out.println("고객: " + customerRepository.querySelectByName("김민종"));
    }

    // 2. 조회 (Read)
    // 모든 고객 조회
    @DisplayName("모든 고객 조회")
    @Test
    public void testQuerySelectAll() {
        List<Customer> customers = customerRepository.querySelectAll();
        for(Customer c : customers) {
            System.out.println("고객: " + c);
        }
    }

    // 고객 아이디로 조회
    @DisplayName("고객 아이디로 조회")
    @Test
    public void testQuerySelectById() {
        Customer customer = customerRepository.querySelectById(5L);
        System.out.println("고객: " + customer);
    }

    // 고객명으 조회
    @DisplayName("고객명으로 조회")
    @Test
    public void testQuerySelectByName() {
        Customer customer = customerRepository.querySelectByName("김길동7");
        System.out.println("고객: " + customer);
    }

    // 고객명 또는 전화번호로 데이터 조회
    @DisplayName("고객명 또는 전화번호로 데이터 조회")
    @Test
    public void testQuerySelectByNameOrPhone() {
        List<Customer> customers = customerRepository.querySelectByNameOrPhone("김길동2", "010-1111-1007");
        for(Customer c : customers) {
            System.out.println("고객: " + c);
        }
    }

    // 고객명과 전화번호가 모두 일치하는 데이터 조회
    @DisplayName("고객명과 전화번호가 모두 일치하는 데이터 조회")
    @Test
    public void testQuerySelectByNameAndPhone() {
        Customer customer = customerRepository.querySelectByNameAndPhone("김길동5", "010-1111-1005");
        System.out.println("고객: " + customer);
    }
    // 특정 나이보다 크거나 같은 데이터 조회
    @DisplayName("특정 나이보다 크거나 같은 데이터 조회")
    @Test
    public void testQuerySelectByAge() {
        List<Customer> customers = customerRepository.querySelectByAge(25);
        for(Customer c : customers) {
            System.out.println("고객: " + c);
        }
    }

    // 특정 나이보다 작거나 같은 데이터를 나이의 내림차순으로 조회
    @DisplayName("특정 나이보다 작거나 같은 데이터를 나이의 내림차순으로 조회")
    @Test
    public void testQuerySelectByAgeDesc() {
        List<Customer> customers = customerRepository.querySelectByAgeDesc(25);
        for(Customer c : customers) {
            System.out.println("고객: " + c);
        }
    }

    // 고객명에 '연'이 포함된 데이터 조회
    @DisplayName("고객명에 '연'이 포함된 데이터 조회")
    @Test
    public void testQuerySelectByNameLikeContain() {
        List<Customer> customers = customerRepository.querySelectByNameLikeContain("연");
        for(Customer c : customers) {
            System.out.println("고객: " + c);
        }
    }

    // 고객명이 '빈'으로 끝나는 데이터 조회
    @DisplayName("고객명이 '빈'으로 끝나는 데이터 조회")
    @Test
    public void testQuerySelectByNameLikeEnd() {
        List<Customer> customers = customerRepository.querySelectByNameLikeEnd("빈");
        for(Customer c : customers) {
            System.out.println("고객: " + c);
        }
    }

    // 고객명이 '손'으로 시작하는 데이터 조회
    @DisplayName("고객명이 '손'으로 시작하는 데이터 조회")
    @Test
    public void testQuerySelectByNameLikeStart() {
        List<Customer> customers = customerRepository.querySelectByNameLikeStart("손");
        for(Customer c : customers) {
            System.out.println("고객: " + c);
        }
    }

    // 나이가 23세에서 26세 사이의 데이터 조회
    @DisplayName("나이가 23세에서 26세 사이의 데이터 조회")
    @Test
    public void testQuerySelectByAgeBetween() {
        List<Customer> customers = customerRepository.querySelectByAgeBetween(23, 26);
        for(Customer c : customers) {
            System.out.println("고객: " + c);
        }
    }

    // 나이가 22, 24, 26세인 데이터 조회
    @DisplayName("나이가 22, 24, 26세인 데이터 조회")
    @Test
    public void testQuerySelectByAgeIn() {
        List<Integer> ages = new ArrayList<Integer>();
        ages.add(22); ages.add(24); ages.add(26);
        List<Customer> customers = customerRepository.querySelectByAgeIn(ages);
        for(Customer c : customers) {
            System.out.println("고객: " + c);
        }
    }

    // 고객아이디로 고객명, 나이, 전화번호를 수정
    @DisplayName("고객아이디로 고객명, 나이, 전화번호를 수정")
    @Test
    public void testUpdateById() {
        Customer customer = Customer.builder().id(1L).name("강백호").age(25).phone("010-2222-2222").build();
        customerRepository.queryUpdateId(customer);
        Customer result = customerRepository.querySelectById(1L);
        System.out.println("수정 후: " + result);
    }

    // 고객아이디로 데이터 삭제 1번 - 아이디로 삭제
    @DisplayName("고객아이디로 데이터 삭제 1번")
    @Test
    public void testDeleteById1() {
        customerRepository.queryDeleteId1(10L);
    }

    // 고객아이디로 데이터 삭제 2번 - 아이디를 객체에 담아서 삭제
    @DisplayName("고객아이디로 데이터 삭제 2번")
    @Test
    public void testDeleteById2() {
        Customer customer = Customer.builder().id(9L).build();
        customerRepository.queryDeleteId2(customer);
    }

}
