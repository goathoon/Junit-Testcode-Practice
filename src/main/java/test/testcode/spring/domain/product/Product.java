package test.testcode.spring.domain.product;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import test.testcode.spring.domain.BaseEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // DB PK 값 생성 역할 (auto increment)
    private Long id;

    private String productNumber;

    @Enumerated(EnumType.STRING) // DB에는 String으로 들어
    private ProductType type;

    @Enumerated(EnumType.STRING) // DB에는 String으로 들어
    private ProductSellingStatus sellingStatus;

    private String name;

    private int price;
}
