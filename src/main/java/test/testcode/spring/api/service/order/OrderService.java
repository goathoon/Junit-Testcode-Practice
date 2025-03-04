package test.testcode.spring.api.service.order;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import test.testcode.spring.api.controller.order.request.OrderCreateRequest;
import test.testcode.spring.api.service.order.response.OrderResponse;
import test.testcode.spring.domain.order.Order;
import test.testcode.spring.domain.order.OrderRepository;
import test.testcode.spring.domain.product.Product;
import test.testcode.spring.domain.product.ProductRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public OrderResponse createOrder(OrderCreateRequest request, LocalDateTime registeredDateTime) {
        List<String> productNumbers = request.getProductNumbers();
        List<Product> products = findProductsBy(productNumbers);

        Order order = Order.create(products, registeredDateTime);
        Order savedOrder = orderRepository.save(order);

        return OrderResponse.of(savedOrder);
    }

    private List<Product> findProductsBy(List<String> productNumbers) {
        List<Product> products = productRepository.findAllByProductNumberIn(productNumbers);

        Map<String, Product> productMap = products.stream()
                .collect(Collectors.toMap(Product::getProductNumber, p -> p));

        return productNumbers.stream()
                .map(productMap::get)
                .collect(Collectors.toList());
    }
}

