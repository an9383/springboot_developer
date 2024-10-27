package org.sb0907.springbootdeveloper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// 데이터베이스에 CRUD를 하는 방법
// 1. 쿼리 메서드를 사용하는 방법
@DataJpaTest
@TestPropertySource(locations = "classpath:application.yml")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional(propagation = Propagation.NOT_SUPPORTED)
class CustomerRepositoryTest01 {
    @Autowired
    CustomerRepository customerRepository;

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
    @Test
    public void testInsertDummies() {
        for(int i=0; i<10; i++) {
            Customer customer = Customer.builder().name("김길동"+i).phone("010-1111-100"+i).age(20+i).build();
            customerRepository.save(customer);
            System.out.println(i + "번 고객 추가: " + customer);
        }
    }

    // ################################################################################################################

    // 3. R(Read, 전체 데이터 조회)
    @DisplayName("전체 데이터 조회")
    //@Test
    public void testFindAll() {
        List<Customer> customers = customerRepository.findAll();

        for(int i=0; i<customers.size(); i++) {
            System.out.println(i + "번 고객 조회: " + customers.get(i));
        }
    }

    // 4. R(Read, 고객아이디로 1건 조회)
    @DisplayName("고객아이디로 1건 조회")
    //@Test
    public void testFindById() {
        //Optional<Customer> customer = customerRepository.findById(5L);
        Customer customer = customerRepository.findById(5L).get();

        System.out.println("고객: " + customer);
    }

    // 5. R(Read, 고객명으로 1건 조회)
    @DisplayName("고객명으로 1건 조회")
    //@Test
    public void testFindByName() {
        Customer customer = customerRepository.findByName("김길동3").get();

        System.out.println("고객: " + customer);
    }

    // 6. R(Read, 고객아이디 또는 고객명으로 조회)
    @DisplayName("고객아이디 또는 고객명으로 조회")
    //@Test
    public void testFindByIdOrName() {
        List<Customer> customers = customerRepository.findByIdOrName(3L, "김길동8");

        for(Customer c : customers) {
            System.out.println("고객: " + c);
        }
    }

    // 7. R(Read, 고객아이디와 고객명이 모두 일치할 때 조회)
    @DisplayName("고객아이디와 고객명이 모두 일치할 때 조회")
    //@Test
    public void testFindByIdAndName() {
        Optional<Customer> customer = customerRepository.findByIdAndName(5L, "김길동4");

        System.out.println("고객: " + customer.get());
    }

    // 나이가 25세보다 큰 고객을 조회
    @DisplayName("나이가 25세보다 큰 고객을 조회")
    //@Test
    public void testFindByAgeGreaterThan() {
        List<Customer> customers = customerRepository.findByAgeGreaterThan(25);

        for(Customer c : customers) {
            System.out.println("고객: " + c);
        }
    }

    // 나이가 25세 이상인 고객을 조회
    @DisplayName("나이가 25세 이상인 고객을 조회")
    //@Test
    public void testFindByAgeGreaterThanEqual() {
        List<Customer> customers = customerRepository.findByAgeGreaterThanEqual(25);

        for(Customer c : customers) {
            System.out.println("고객: " + c);
        }
    }

    // 나이가 25세보다 작은 고객을 조회
    @DisplayName("나이가 25세보다 작은 고객을 조회")
    //@Test
    public void testFindByAgeLessThan() {
        List<Customer> customers = customerRepository.findByAgeLessThan(25);

        for(Customer c : customers) {
            System.out.println("고객: " + c);
        }
    }

    // 나이가 25세 이하인 고객을 조회
    @DisplayName("나이가 25세 이하인 고객을 조회")
    //@Test
    public void testFindByAgeLessThanEqual() {
        List<Customer> customers = customerRepository.findByAgeLessThanEqual(25);

        for(Customer c : customers) {
            System.out.println("고객: " + c);
        }
    }

    // 나이가 25세인 고객을 조회
    @DisplayName("나이가 25세인 고객을 조회")
    //@Test
    public void testFindByAgeEquals() {
        List<Customer> customers = customerRepository.findByAgeEquals(25);

        for(Customer c : customers) {
            System.out.println("고객: " + c);
        }
    }

    // 나이가 25세인 고객을 조회
    @DisplayName("나이가 25세인 고객을 조회")
    //@Test
    public void testFindByAge() {
        List<Customer> customers = customerRepository.findByAge(25);

        for(Customer c : customers) {
            System.out.println("고객: " + c);
        }
    }

    // 나이가 25세가 아닌 고객을 조회
    @DisplayName("나이가 25세가 아닌 고객을 조회")
    //@Test
    public void testFindByAgeNot() {
        List<Customer> customers = customerRepository.findByAgeNot(25);

        for(Customer c : customers) {
            System.out.println("고객: " + c);
        }
    }

    // 나이가 23세(포함)에서 27세(포함)까지인 고객을 조회
    @DisplayName("나이가 23세(포함)에서 27세(포함)까지인 고객을 조회")
    //@Test
    public void testFindByAgeBetween() {
        List<Customer> customers = customerRepository.findByAgeBetween(23, 27);

        for(Customer c : customers) {
            System.out.println("고객: " + c);
        }
    }

    // 나이가 23, 25, 27세인 고객을 조회
    @DisplayName("나이가 23, 25, 27세인 고객을 조회")
    //@Test
    public void testFindByAgeIn() {
        List<Integer> ages = new ArrayList<Integer>();
        ages.add(23); ages.add(25); ages.add(27);
        List<Customer> customers = customerRepository.findByAgeIn(ages);

        for(Customer c : customers) {
            System.out.println("고객: " + c);
        }
    }

    // 나이가 23, 25, 27세가 아닌 고객을 조회
    @DisplayName("나이가 23, 25, 27세가 아닌 고객을 조회")
    //@Test
    public void testFindByAgeNotIn() {
        List<Integer> ages = new ArrayList<Integer>();
        ages.add(23); ages.add(25); ages.add(27);
        List<Customer> customers = customerRepository.findByAgeNotIn(ages);

        for(Customer c : customers) {
            System.out.println("고객: " + c);
        }
    }

    // 고객명에 '철'을 포함하는 고객을 조회
    @DisplayName("고객명에 '철'을 포함하는 고객을 조회")
    //@Test
    public void testFindByNameLike() {
        List<Customer> customers = customerRepository.findByNameLike("%철%");

        for(Customer c : customers) {
            System.out.println("고객: " + c);
        }
    }

    // 고객명에 '철'을 포함하지 않는 고객을 조회
    @DisplayName("고객명에 '철'을 포함하지 않는 고객을 조회")
    //@Test
    public void testFindByNameNotLike() {
        List<Customer> customers = customerRepository.findByNameNotLike("%철%");

        for(Customer c : customers) {
            System.out.println("고객: " + c);
        }
    }

    // 고객명에 '철'을 포함하는 고객을 조회 -> 와일드카드 기호 사용하지 않음
    @DisplayName("고객명에 '철'을 포함하는 고객을 조회")
    //@Test
    public void testFindByNameContaining() {
        List<Customer> customers = customerRepository.findByNameContaining("철");

        for(Customer c : customers) {
            System.out.println("고객: " + c);
        }
    }

    // 고객명에서 '박'으로 시작하는 고객을 조회 -> 와일드카드 기호 사용하지 않음
    @DisplayName("고객명에서 '박'으로 시작하는 고객을 조회")
    //@Test
    public void testFindByNameStartingWith() {
        List<Customer> customers = customerRepository.findByNameStartingWith("박");

        for(Customer c : customers) {
            System.out.println("고객: " + c);
        }
    }

    // 고객명에서 '민'으로 끝나는 고객을 조회 -> 와일드카드 기호 사용하지 않음
    @DisplayName("고객명에서 '민'으로 끝나는 고객을 조회")
    //@Test
    public void testFindByNameEndingWith() {
        List<Customer> customers = customerRepository.findByNameEndingWith("민");

        for(Customer c : customers) {
            System.out.println("고객: " + c);
        }
    }

    // 나이가 23세 이상인 고객의 정보를 아이디에 대한 내림차순으로 조회
    @DisplayName("나이가 23세 이상인 고객의 정보를 아이디에 대한 내림차순으로 조회")
    //@Test
    public void testFindByAgeGreaterThanEqualOrderByIdDesc() {
        List<Customer> customers = customerRepository.findByAgeGreaterThanEqualOrderByIdDesc(23);

        for(Customer c : customers) {
            System.out.println("고객: " + c);
        }
    }

    // 고객명에 대해서 아이디를 기준으로 내림차순하여 2건을 조회
    @DisplayName("고객명에 대해서 아이디를 기준으로 내림차순하여 2건을 조회")
    //@Test
    public void testFindTop2ByNameOrderByIdDesc() {
        List<Customer> customers = customerRepository.findTop2ByNameOrderByIdDesc("홍길동");

        for(Customer c : customers) {
            System.out.println("고객: " + c);
        }
    }

    // #############################################################################################################
    // 데이터 수정 방법 1번 - @Transactional을 사용하는 방법
    @DisplayName("데이터 수정 방법 1번")
    //@Test
    @Transactional
    @Commit
    public void testUpdateWithTransaction() {
        Optional<Customer> customer = customerRepository.findById(10L);
        Customer c = customer.get();
        System.out.println("수정 전: " + c);

        // 값을 변경
        c.setName("김연아");
        c.setPhone("010-7777-7777");
        c.setAge(31);

        System.out.println("수정 후: " + c);
    }

    // 데이터 수정 방법 1번 - @Transactional을 사용하지 않는 방법 ->  save() 메서드를 사용
    @DisplayName("데이터 수정 방법 2번")
    //@Test
    //@Transactional
    //@Commit
    public void testUpdateWithoutTransaction() {
        Optional<Customer> customer = customerRepository.findById(9L);
        Customer c = customer.get();
        System.out.println("수정 전: " + c);

        // 값을 변경
        c.setName("신유빈");
        c.setPhone("010-9999-9999");
        c.setAge(29);

        // save() 메서드 사용
        customerRepository.save(c);

        System.out.println("수정 후: " + c);
    }

    // ########################################################################################################
    // 데이터 삭제 방법 1번 - deleteById() 메서드 사용
    @DisplayName("데이터 삭제 방법 1번")
    //@Test
    public void testDeleteById() {
        customerRepository.deleteById(1L);
    }

    // 데이터 삭제 방법 2번 - delete() 메서드 사용
    @DisplayName("데이터 삭제 방법 2번")
    //@Test
    public void testDelete() {
        Optional<Customer> customer = customerRepository.findById(2L);
        customerRepository.delete(customer.get());
    }

    //  데이터 삭제 방법 3번 - 특정 변수명으로 삭제
    @DisplayName("고객명으로 삭제")
    //@Test
    public void testDeleteByName() {
        customerRepository.deleteByName("김연아");
    }

    // 데이터 삭제 방법 4번 - 모든 데이터 삭제
    @DisplayName("모든 데이터 삭제")
    //@Test
    public void testDeleteAll() {
        customerRepository.deleteAll();
    }
}