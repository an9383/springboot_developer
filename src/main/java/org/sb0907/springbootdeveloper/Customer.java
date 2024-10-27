package org.sb0907.springbootdeveloper;


import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder // 빌더 패턴으로 객체를 생성
@Entity  // 클래스가 테이블과 매핑됨
public class Customer {
    @Id // Primary Key 설정
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(length = 15, nullable = false)
    private String phone;

    @Column(columnDefinition = "smallint not null")
    private int age;
}
