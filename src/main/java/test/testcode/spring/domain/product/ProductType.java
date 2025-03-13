package test.testcode.spring.domain.product;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor // 이거 없으면 생성 안됨
@Getter
public enum ProductType {
    HANDMADE("제조 음료"),
    BOTTLE("병 음료"),
    BAKERY("베이커리");
    private final String text;


    public static boolean containsStockType(ProductType type) {
        return List.of(BOTTLE,BAKERY).contains(type);
    }
}
