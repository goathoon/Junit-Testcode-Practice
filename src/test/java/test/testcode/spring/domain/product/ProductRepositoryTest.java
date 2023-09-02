package test.testcode.spring.domain.product;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.*;
import static test.testcode.spring.domain.product.ProductSellingStatus.*;
import static test.testcode.spring.domain.product.ProductType.*;
@ActiveProfiles("test") //test profile로 설정 (sql init x)
@DataJpaTest
class ProductRepositoryTest {
    @Autowired
    private ProductRepository productRepository;

    @DisplayName("원하는 판매 상태를 가진 상품들을 조회")
    @Test
    void findAllBySellingStatusIn() {
        //given
        Product product1 = Product.builder()
                .productNumber("001")
                .type(HANDMADE)
                .sellingStatus(SELLING)
                .name("아메리카노")
                .price(4000)
                .build();
        Product product2 = Product.builder()
                .productNumber("002")
                .type(HANDMADE)
                .sellingStatus(HOLD)
                .name("카페라떼")
                .price(4500)
                .build();
        Product product3 = Product.builder()
                .productNumber("003")
                .type(HANDMADE)
                .sellingStatus(STOP_SELLING)
                .name("팥빙수")
                .price(7000)
                .build();
        productRepository.saveAll(List.of(product1, product2, product3));

        //when
        List<Product> products = productRepository.findALlBySellingStatusIn(List.of(SELLING, HOLD));
        //then
        assertThat(products).hasSize(2) //
                .extracting("productNumber", "name", "sellingStatus") // 검증하고자 하는 필드만 추출
                .containsExactlyInAnyOrder(
                        tuple("001", "아메리카노", SELLING),
                        tuple("002", "카페라떼", HOLD)
                );
    }
}