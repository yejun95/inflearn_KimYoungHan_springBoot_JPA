package jpabook.jpashop.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable ///JPA 내장 타입
@Getter
public class Address {

    private String city;
    private String street;
    private String zipcode;

    //@Embeddable 쓰려면 public or protected 생성자 필요 -> protected로 해서 조금의 안전성을 준다.
    protected Address() {
    }

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
