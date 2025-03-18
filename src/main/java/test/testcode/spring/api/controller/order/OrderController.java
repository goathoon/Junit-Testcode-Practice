package test.testcode.spring.api.controller.order;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import test.testcode.spring.api.ApiResponse;
import test.testcode.spring.api.controller.order.request.OrderCreateRequest;
import test.testcode.spring.api.service.order.OrderService;
import test.testcode.spring.api.service.order.response.OrderResponse;
import test.testcode.spring.domain.order.OrderRepository;

import javax.validation.Valid;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@RestController
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/api/v1/orders/new")
    public ApiResponse<OrderResponse> createOrder(@Valid @RequestBody OrderCreateRequest request) {
        LocalDateTime registeredDateTime = LocalDateTime.now();
        // 서비스 레이어가 상위 레이어인 컨트롤러 Request 객체를 알고 있는 것은 의존성이 생겨버림
        // 그것 때문에 RequestDto를 서비스 레이어로 내릴때 다른(서비스용)  DTO로 변환해주자.
        return ApiResponse.ok(orderService.createOrder(request.toServiceRequest(), registeredDateTime));
    }
}
