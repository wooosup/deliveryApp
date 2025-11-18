package hello.delivery.order.controller;


import hello.delivery.common.api.ApiResponse;
import hello.delivery.order.controller.response.OrderResponse;
import hello.delivery.order.domain.Order;
import hello.delivery.order.domain.OrderCreate;
import hello.delivery.order.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "주문")
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/new")
    @Parameter(in = ParameterIn.HEADER, name = "USERNAME")
    @Operation(summary = "주문 생성", description = "새로운 주문을 생성합니다.")
    public ApiResponse<OrderResponse> order(
            @Parameter(name = "USERNAME", in = ParameterIn.HEADER) @RequestHeader("USERNAME") String username,
            @Valid @RequestBody OrderCreate request) {
        Order order = orderService.order(username, request);
        return ApiResponse.ok(OrderResponse.of(order));
    }

    @GetMapping("/my-orders")
    @Parameter(in = ParameterIn.HEADER, name = "USERNAME")
    @Operation(summary = "내 주문 조회", description = "로그인한 사용자의 모든 주문을 조회합니다.")
    public ApiResponse<List<OrderResponse>> getMyOrders(
            @Parameter(name = "USERNAME", in = ParameterIn.HEADER) @RequestHeader("USERNAME") String username) {
        List<Order> orders = orderService.findOrdersByUsername(username);
        return ApiResponse.ok(OrderResponse.of(orders));
    }
}

