package org.sb0907.springbootdeveloper;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity // 클래스가 DBMS의 테이블이 됨.
public class Member {
    @Id // Primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 1부터 자동으로 1씩 증가
    @Column(name = "id", updatable = false) // 변경 불가
    private Long id;

    @Column(name = "name", updatable = false) // 변경 불가
    private String name;
}
