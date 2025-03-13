package test.testcode.spring.domain.stuck;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import test.testcode.spring.domain.product.Product;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.*;
import static test.testcode.spring.domain.product.ProductSellingStatus.*;
import static test.testcode.spring.domain.product.ProductType.HANDMADE;

@DataJpaTest
class StockRepositoryTest {

    @Autowired
    StockRepository stockRepository;

    @DisplayName("상품번호 리스트로 재고를 조회한다.")
    @Test
    void findAllByProductNumberIn() {
        //given
        Stock stock1 = Stock.create("001", 1);
        Stock stock2 = Stock.create("002", 2);
        Stock stock3 = Stock.create("003", 3);
        stockRepository.saveAll(List.of(stock1,stock2,stock3));

        //when
        List<Stock> stocks = stockRepository.findAllByProductNumberIn(List.of("001", "002"));
        //then
        assertThat(stocks).hasSize(2) //
                .extracting("productNumber", "quantity") // 검증하고자 하는 필드만 추출
                .containsExactlyInAnyOrder(
                        tuple("001", 1),
                        tuple("002", 2 )
                );
    }
}