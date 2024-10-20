package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Delivery {

    @Id
    @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    // order 테이블에 delivery를 FK로 쓰겠따 명시
    @OneToOne(mappedBy = "delivery") // 연관 관계의 주인이 아님을 명시 -> 즉, 컬럼이 생성되지 않음
    private Order order;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status; // READY, COMP
}
