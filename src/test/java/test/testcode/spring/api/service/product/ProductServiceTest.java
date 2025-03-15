package test.testcode.spring.api.service.product;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import test.testcode.spring.api.controller.product.dto.request.ProductCreateRequest;
import test.testcode.spring.api.service.product.response.ProductResponse;
import test.testcode.spring.domain.product.Product;
import test.testcode.spring.domain.product.ProductRepository;
import test.testcode.spring.domain.product.ProductSellingStatus;
import test.testcode.spring.domain.product.ProductType;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static test.testcode.spring.domain.product.ProductSellingStatus.*;
import static test.testcode.spring.domain.product.ProductType.HANDMADE;

@SpringBootTest
@ActiveProfiles("test")
class ProductServiceTest {

    @Autowired
    ProductService productService;

    @Autowired
    ProductRepository productRepository;

    @AfterEach
    void tearDown() {
        productRepository.deleteAllInBatch();
    }

    @DisplayName("신규 상품을 등록한다. 상품번호는 가장 최근 등록된 상품의 상품 번호에서 1 증가한 값이다.")
    @Test
    void createProduct() {
        // given
        Product product = createProduct("001", HANDMADE, SELLING, "아메리카노", 4000);
        productRepository.save(product);

        ProductCreateRequest request = ProductCreateRequest.builder()
                .type(HANDMADE)
                .sellingStatus(SELLING)
                .name("카푸치노")
                .price(5000)
                .build();

        // when
        ProductResponse productResponse = productService.createProduct(request);

        // then
        assertThat(productResponse)
                .extracting("productNumber", "type", "sellingStatus", "name", "price")
                .contains("002", HANDMADE, SELLING, "카푸치노", 5000);

        List<Product> products = productRepository.findAll();
        assertThat(products).hasSize(2)
                .extracting("productNumber", "type", "sellingStatus", "name", "price")
                .containsExactlyInAnyOrder(
                        tuple("001",HANDMADE,SELLING,"아메리카노",4000),
                        tuple("002",HANDMADE,SELLING,"카푸치노",5000)
                );
    }

    @DisplayName("상품이 하나도 없는 경우, 신규 상품을 등록하면 상품번호는 001이다")
    @Test
    void createProductWhenProductIsEmpty() {
        // given
        ProductCreateRequest request = ProductCreateRequest.builder()
                .type(HANDMADE)
                .sellingStatus(SELLING)
                .name("카푸치노")
                .price(5000)
                .build();

        // when
        ProductResponse productResponse = productService.createProduct(request);

        // then
        assertThat(productResponse)
                .extracting("productNumber", "type", "sellingStatus", "name", "price")
                .contains("001", HANDMADE, SELLING, "카푸치노", 5000);

        List<Product> products = productRepository.findAll();
        assertThat(products).hasSize(1)
                .extracting("productNumber", "type", "sellingStatus", "name", "price")
                .contains(
                        tuple("001",HANDMADE,SELLING,"카푸치노",5000)
                );
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