package test.testcode.spring.domain.product;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Getter
public enum ProductSellingStatus {
    SELLING("판매 상태"),
    HOLD("판매 보류"),
    STOP_SELLING("판매 중");
    private final String text;

    public static List<ProductSellingStatus> forDisplay() {
        return List.of(SELLING,HOLD);
    }
}
