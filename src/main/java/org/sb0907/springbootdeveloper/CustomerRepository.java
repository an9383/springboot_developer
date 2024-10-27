package org.sb0907.springbootdeveloper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // 1. 쿼리 메서드를 사용하는 방법
    // 고객명으로 조회
    Optional<Customer> findByName(String name);
    // 고객아이디 또는 고객명으로 조회
    List<Customer> findByIdOrName(Long id, String name);
    // 고객아이디와 고객명이 모두 일치할 때 조회
    Optional<Customer> findByIdAndName(Long id, String name);
    // 나이가 25세보다 큰 고객을 조회
    List<Customer> findByAgeGreaterThan(int age);
    // 나이가 25세 이상인 고객을 조회
    List<Customer> findByAgeGreaterThanEqual(int age);
    // 나이가 25세보다 작은 고객을 조회
    List<Customer> findByAgeLessThan(int age);
    // 나이가 25세 이하인 고객을 조회
    List<Customer> findByAgeLessThanEqual(int age);
    // 나이가 25세인 고객을 조회
    List<Customer> findByAgeEquals(int age);
    List<Customer> findByAge(int age);
    // 나이가 25세가 아닌 고객을 조회
    List<Customer> findByAgeNot(int age);
    // 나이가 23세(포함)에서 27세(포함)까지인 고객을 조회
    List<Customer> findByAgeBetween(int start, int end);
    // 나이가 23, 25, 27세인 고객을 조회
    List<Customer> findByAgeIn(List<Integer> ages);
    // 나이가 23, 25, 27세가 아닌 고객을 조회
    List<Customer> findByAgeNotIn(List<Integer> ages);
    // 고객명에 '철'을 포함하는 고객을 조회
    List<Customer> findByNameLike(String name);
    // 고객명에 '철'을 포함하지 않는 고객을 조회
    List<Customer> findByNameNotLike(String name);
    // 고객명에 '철'을 포함하는 고객을 조회 -> 와일드카드 기호 사용하지 않음
    List<Customer> findByNameContaining(String name);
    // 고객명에서 '박'으로 시작하는 고객을 조회 -> 와일드카드 기호 사용하지 않음
    List<Customer> findByNameStartingWith(String name);
    // 고객명에서 '민'으로 끝나는 고객을 조회 -> 와일드카드 기호 사용하지 않음
    List<Customer> findByNameEndingWith(String name);
    // 나이가 23세 이상인 고객의 정보를 아이디에 대한 내림차순으로 조회
    List<Customer> findByAgeGreaterThanEqualOrderByIdDesc(int age);
    // 고객명에 대해서 아이디를 기준으로 내림차순하여 2건을 조회
    List<Customer> findTop2ByNameOrderByIdDesc(String name);

    // 고객명으로 삭제
    @Transactional
    void deleteByName(String name);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // 2. @Query 애너테이션을 사용하는 방법
    // 고객 데이터 추가
    String INSERT_QUERY = "insert into customer(name, age, phone) values(:#{#customer.name}, :#{#customer.age}, :#{#customer.phone})";
    @Transactional
    @Modifying
    @Query(value = INSERT_QUERY, nativeQuery = true)
    void queryInsert(@Param("customer") Customer customer);

    // 모든 고객 조회
    String SELECT_ALL_QUERY = "select * from customer";
    @Query(value = SELECT_ALL_QUERY, nativeQuery = true)
    List<Customer> querySelectAll();

    // 고객아이디로 데이터 조회
    String SELECT_ID_QUERY = "select * from customer where id = :id";
    @Query(value = SELECT_ID_QUERY, nativeQuery = true)
    Customer querySelectById(@Param("id") Long id);

    // 고객명로 데이터 조회
    String SELECT_NAME_QUERY = "select * from customer where name = :name";
    @Query(value = SELECT_NAME_QUERY, nativeQuery = true)
    Customer querySelectByName(@Param("name") String name);

    // 고객명 또는 전화번호로 데이터 조회
    String SELECT_NAME_PHONE_QUERY1 = "select * from customer where name = :name or phone = :phone";
    @Query(value = SELECT_NAME_PHONE_QUERY1, nativeQuery = true)
    List<Customer> querySelectByNameOrPhone(@Param("name") String name, @Param("phone") String phone);

    // 고객명과 전화번호가 모두 일치하는 데이터 조회
    String SELECT_NAME_PHONE_QUERY2 = "select * from customer where name = :name and phone = :phone";
    @Query(value = SELECT_NAME_PHONE_QUERY2, nativeQuery = true)
    Customer querySelectByNameAndPhone(@Param("name") String name, @Param("phone") String phone);

    // 특정 나이보다 크거나 같은 데이터 조회
    String SELECT_AGE_QUERY1 = "select * from customer where age >= :age";
    @Query(value = SELECT_AGE_QUERY1, nativeQuery = true)
    List<Customer> querySelectByAge(@Param("age") int age);

    // 특정 나이보다 작거나 같은 데이터를 나이의 내림차순으로 조회
    String SELECT_AGE_QUERY2 = "select * from customer where age <= :age order by age desc";
    @Query(value = SELECT_AGE_QUERY2, nativeQuery = true)
    List<Customer> querySelectByAgeDesc(@Param("age") int age);

    // 고객명에 '연'이 포함된 데이터 조회
    String SELECT_NAME_LIKE_QUERY1 = "select * from customer where name like %:name%";
    @Query(value = SELECT_NAME_LIKE_QUERY1, nativeQuery = true)
    List<Customer> querySelectByNameLikeContain(@Param("name") String name);

    // 고객명이 '빈'으로 끝나는 데이터 조회
    String SELECT_NAME_LIKE_QUERY2 = "select * from customer where name like %:name";
    @Query(value = SELECT_NAME_LIKE_QUERY2, nativeQuery = true)
    List<Customer> querySelectByNameLikeEnd(@Param("name") String name);

    // 고객명이 '손'으로 시작하는 데이터 조회
    String SELECT_NAME_LIKE_QUERY3 = "select * from customer where name like :name%";
    @Query(value = SELECT_NAME_LIKE_QUERY3, nativeQuery = true)
    List<Customer> querySelectByNameLikeStart(@Param("name") String name);

    // 나이가 23세에서 26세 사이의 데이터 조회
    String SELECT_AGE_BETWEEN_QUERY = "select * from customer where age between :start and :end";
    @Query(value = SELECT_AGE_BETWEEN_QUERY, nativeQuery = true)
    List<Customer> querySelectByAgeBetween(@Param("start") int start, @Param("end") int end);

    // 나이가 22, 24, 26세인 데이터 조회
    String SELECT_AGE_IN_QUERY = "select * from customer where age in (:ages)";
    @Query(value = SELECT_AGE_IN_QUERY, nativeQuery = true)
    List<Customer> querySelectByAgeIn(@Param("ages") List<Integer> ages);

    // 고객아이디로 고객명, 나이, 전화번호를 수정
    // 객체의 값을 매핑할 때 주의
    // @Transactional, @Modifying 애너테이션을 써주어야 함
    String UPDATE_CUSTOMER_QUERY = "update customer set name = :#{#customer.name}, age = :#{#customer.age}, phone = :#{#customer.phone} where id = :#{#customer.id}";
    @Transactional
    @Modifying
    @Query(value = UPDATE_CUSTOMER_QUERY, nativeQuery = true)
    void queryUpdateId(@Param("customer") Customer customer);

    // 고객아이디로 데이터 삭제 1번 - 아이디로 삭제
    String DELETE_CUSTOMER_QUERY1 = "delete from customer where id = :id";
    @Transactional
    @Modifying
    @Query(value = DELETE_CUSTOMER_QUERY1, nativeQuery = true)
    void queryDeleteId1(@Param("id") Long id);

    // 고객아이디로 데이터 삭제 2번 - 아이디를 객체에 담아서 삭제
    String DELETE_CUSTOMER_QUERY2 = "delete from customer where id = :#{#customer.id}";
    @Transactional
    @Modifying
    @Query(value = DELETE_CUSTOMER_QUERY2, nativeQuery = true)
    void queryDeleteId2(@Param("customer") Customer customer);

}
