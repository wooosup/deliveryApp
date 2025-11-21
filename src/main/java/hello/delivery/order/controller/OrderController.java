package hello.delivery.order.controller;


import hello.delivery.common.annotation.LoginUser;
import hello.delivery.common.api.ApiResponse;
import hello.delivery.order.controller.response.OrderResponse;
import hello.delivery.order.domain.Order;
import hello.delivery.order.domain.OrderCreate;
import hello.delivery.order.service.OrderService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController implements OrderControllerDocs {

    private final OrderService orderService;

    @Override
    @PostMapping("/new")
    public ApiResponse<OrderResponse> order(@LoginUser Long userId,
                                            @Valid @RequestBody OrderCreate request) {
        Order order = orderService.order(userId, request);
        return ApiResponse.ok(OrderResponse.of(order));
    }

    @Override
    @GetMapping("/my-orders")
    public ApiResponse<List<OrderResponse>> getMyOrders(@LoginUser Long userId) {
        List<Order> orders = orderService.findOrdersByUserId(userId);
        return ApiResponse.ok(OrderResponse.of(orders));
    }

}

