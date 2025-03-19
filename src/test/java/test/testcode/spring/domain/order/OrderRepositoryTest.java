package test.testcode.spring.domain.order;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import test.testcode.spring.domain.orderproduct.OrderProductRepository;
import test.testcode.spring.domain.product.Product;
import test.testcode.spring.domain.product.ProductRepository;
import test.testcode.spring.domain.product.ProductSellingStatus;
import test.testcode.spring.domain.product.ProductType;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class OrderRepositoryTest {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    OrderProductRepository orderProductRepository;

    @AfterEach
    void tearDown() {
        orderProductRepository.deleteAllInBatch();
        productRepository.deleteAllInBatch();
        orderRepository.deleteAllInBatch();
    }

    @DisplayName("결제 완료된 상품들 중에서 특정 날짜 기준으로 가져온다.")
    @Test
    void findOrdersByPaymentSucceedFromDateToEndDate() {
        // given
        LocalDateTime registerTime = LocalDateTime.now();
        Product product1 = createProduct("001", ProductType.HANDMADE, ProductSellingStatus.SELLING, "아메리카노", 2000);
        Product product2 = createProduct("002", ProductType.HANDMADE, ProductSellingStatus.SELLING, "아이스라떼", 3000);
        Product product3 = createProduct("003", ProductType.HANDMADE, ProductSellingStatus.SELLING, "콩국수", 4000);
        productRepository.saveAll(List.of(product1, product2, product3));

        Order order1 = Order.create(List.of(product1, product2, product3), registerTime);
        Order order2 = Order.create(List.of(product1, product2, product3), registerTime.minusDays(1L));
        Order order3 = Order.create(List.of(product1, product2, product3), registerTime.minusDays(2L));
        Order order4 = Order.create(List.of(product1, product2, product3), registerTime);

        order1.updateStatusToPaymentCompleted();
        order2.updateStatusToPaymentCompleted();
        order3.updateStatusToPaymentCompleted();

        orderRepository.saveAll(List.of(order1,order2,order3,order4));
        System.out.println("===== save =====");
//        orderRepository.flush(); // 여기 유의!!!!
        System.out.println("===== after flush =====");

        // when
        List<Order> result = orderRepository.findOrdersBy(registerTime, registerTime.plusDays(1L), OrderStatus.PAYMENT_COMPLETED);

        // then
        assertThat(result).hasSize(1);
    }

    @DisplayName("결제 완료된 상품이 없을때 중에서 특정 날짜 기준으로 가져오면 아무것도 없다")
    @Test
    void EmptyOrdersByPaymentSucceedFromDateToEndDate() {
        // given
        LocalDateTime registerTime = LocalDateTime.now();
        Product product1 = createProduct("001", ProductType.HANDMADE, ProductSellingStatus.SELLING, "아메리카노", 2000);
        Product product2 = createProduct("002", ProductType.HANDMADE, ProductSellingStatus.SELLING, "아이스라떼", 3000);
        Product product3 = createProduct("003", ProductType.HANDMADE, ProductSellingStatus.SELLING, "콩국수", 4000);
        productRepository.saveAll(List.of(product1, product2, product3));

        Order order1 = Order.create(List.of(product1, product2, product3), registerTime);
        Order order2 = Order.create(List.of(product1, product2, product3), registerTime.minusDays(1L));
        Order order3 = Order.create(List.of(product1, product2, product3), registerTime.minusDays(2L));
        Order order4 = Order.create(List.of(product1, product2, product3), registerTime);

        orderRepository.saveAll(List.of(order1,order2,order3,order4));

        // when
        List<Order> result = orderRepository.findOrdersBy(registerTime, registerTime.plusDays(1L), OrderStatus.PAYMENT_COMPLETED);

        // then
        assertThat(result).isEmpty();
    }

    private Product createProduct(String number, ProductType type, ProductSellingStatus sellingStatus, String name, int price) {
        return Product.builder()
                .productNumber(number)
                .type(type)
                .sellingStatus(sellingStatus)
                .name(name)
                .price(price)
                .build();
    }
}
