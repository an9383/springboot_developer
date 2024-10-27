package org.sb0907.springbootdeveloper;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// 데이터베이스에 CRUD를 하는 방법
// 3. QueryDSL을 사용하는 방법
// insert() 메서드는 제공하지 않음
@DataJpaTest
@TestPropertySource(locations = "classpath:application.yml")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class CustomerRepositoryTest03 {
    @Autowired
    CustomerRepository customerRepository;

    // Entity 관리 객체
    @PersistenceContext
    EntityManager em;

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // 1. C(Create, 데이터 1건 추가)
    @DisplayName("데이터 1건 추가")
    //@Test
    public void testInsert() {
        //Customer customer = new Customer(1L, "김일번", "010-1111-1111", 15); // 생성자를 사용한 객체 생성
        //빌더 패턴으로 객체 생성
        Customer customer = Customer.builder().name("김일번").phone("010-1111-1111").age(15).build();
        customerRepository.save(customer);
        System.out.println("고객 1명 추가: " + customer);
    }

    // 2. C(Create, 데이터 10건 추가)
    @DisplayName("데이터 10건 추가")
    //@Test
    public void testInsertDummies() {
        for(int i=0; i<10; i++) {
            Customer customer = Customer.builder().name("김길동"+i).phone("010-1111-100"+i).age(20+i).build();
            customerRepository.save(customer);
            System.out.println(i + "번 고객 추가: " + customer);
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // R(Read, 전체 데이터 조회)
    @DisplayName("QueryDSL - 전체 데이터 조회")
    @Test
    public void testFindAll() {
        // 쿼리를 동적으로 생성하는 객체
        JPAQueryFactory factory = new JPAQueryFactory(em);
        // Entity를 복제해서 가지고 있는 객체
        QCustomer qCustomer = QCustomer.customer;

        List<Customer> customerList = factory.selectFrom(qCustomer).fetch();
        for(Customer c : customerList) {
            System.out.println("고객: " + c);
        }
    }

    // R(Read, 고객 아이디로 조회)
    @DisplayName("QueryDSL - 고객 아이디로 조회")
    @Test
    public void testFindById() {
        JPAQueryFactory factory = new JPAQueryFactory(em);
        QCustomer qCustomer = QCustomer.customer;

        Customer customer = factory.selectFrom(qCustomer).where(qCustomer.id.eq(5L)).fetchOne();
        System.out.println("고객: " + customer);
    }

    // 고객아이디가 5번 또는 고객명이 김길동6인 데이터 조회
    @DisplayName("QueryDSL - 고객 아이디 또는 고객명으로 조회")
    @Test
    public void testFindByIdOrName() {
        JPAQueryFactory factory = new JPAQueryFactory(em);
        QCustomer qCustomer = QCustomer.customer;

        List<Customer> customerList = factory.selectFrom(qCustomer).where(qCustomer.id.eq(5L).or(qCustomer.name.eq("김길동6"))).fetch();
        for(Customer c : customerList) {
            System.out.println("고객: " + c);
        }
    }

    // 25세보다 작은 데이터 조회
    @DisplayName("QueryDSL - 특정 나이로 조회1")
    @Test
    public void testFindByAge1() {
        JPAQueryFactory factory = new JPAQueryFactory(em);
        QCustomer qCustomer = QCustomer.customer;

        List<Customer> customerList = factory.selectFrom(qCustomer).where(qCustomer.age.lt(25)).fetch();
        for(Customer c : customerList) {
            System.out.println("고객: " + c);
        }
    }

    // 25세보다 크거나 같은 데이터를 나이의 내림차순으로 조회
    @DisplayName("QueryDSL - 특정 나이로 조회2")
    @Test
    public void testFindByAge2() {
        JPAQueryFactory factory = new JPAQueryFactory(em);
        QCustomer qCustomer = QCustomer.customer;

        List<Customer> customerList = factory.selectFrom(qCustomer)
                .where(qCustomer.age.goe(25)).orderBy(qCustomer.age.desc()).fetch();
        for(Customer c : customerList) {
            System.out.println("고객: " + c);
        }
    }


    // 23세부터 26세까지의 데이터를 나이의 내림차순으로 조회
    @DisplayName("QueryDSL - 특정 나이로 조회3")
    @Test
    public void testFindByAge3() {
        JPAQueryFactory factory = new JPAQueryFactory(em);
        QCustomer qCustomer = QCustomer.customer;

        List<Customer> customerList = factory.selectFrom(qCustomer).where(qCustomer.age.between(23,26))
                .orderBy(qCustomer.age.desc()).fetch();
        for(Customer c : customerList) {
            System.out.println("고객: " + c);
        }
    }

    // 22, 24, 27세의 데이터를 나이의 내림차순으로 조회
    @DisplayName("QueryDSL - 특정 나이로 조회4")
    @Test
    public void testFindByAge4() {
        JPAQueryFactory factory = new JPAQueryFactory(em);
        QCustomer qCustomer = QCustomer.customer;

        List<Customer> customerList = factory.selectFrom(qCustomer).where(qCustomer.age.in(22,24,27))
                .orderBy(qCustomer.age.desc()).fetch();
        for(Customer c : customerList) {
            System.out.println("고객: " + c);
        }
    }

    // 고객명 특정단어가 포함된 데이터를 조회
    // 고객명에 7이 포함된 데이터 조회
    @DisplayName("QueryDSL - 고객명에 포함된 데이터 조회")
    @Test
    public void testFindByNameContains() {
        JPAQueryFactory factory = new JPAQueryFactory(em);
        QCustomer qCustomer = QCustomer.customer;

        List<Customer> customerList = factory.selectFrom(qCustomer).where(qCustomer.name.contains("7")).fetch();
        for(Customer c : customerList) {
            System.out.println("고객: " + c);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // 3. U(Update, 수정)
    // 9번 아이디 고객의 고객의 정보(고객명, 전화번호, 나이)를 수정
    @DisplayName("QueryDSL - 데이터 수정")
    @Test
    @Transactional
    @Commit
    public void testUpdate() {
        JPAQueryFactory factory = new JPAQueryFactory(em);
        QCustomer qCustomer = QCustomer.customer;

        long count = factory.update(qCustomer).set(qCustomer.name, "홍길동").set(qCustomer.phone, "010-9999-9999").set(qCustomer.age, 33)
                .where(qCustomer.id.eq(9L)).execute();
        System.out.println("수정 건수: " + count);

        Customer customer = factory.selectFrom(qCustomer).where(qCustomer.id.eq(9L)).fetchOne();
        System.out.println("수정 고객: " + customer);
    }

    // 4. D(Delete, 데이터 삭제)
    // 10번 고객 데이터 삭제
    @DisplayName("QueryDSL - 데이터 삭제")
    @Test
    @Transactional
    @Commit
    public void testDeleteById() {
        JPAQueryFactory factory = new JPAQueryFactory(em);
        QCustomer qCustomer = QCustomer.customer;

        long count = factory.delete(qCustomer).where(qCustomer.id.eq(10L)).execute();
        System.out.println("삭제 건수: " + count);
    }

}

