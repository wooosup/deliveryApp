package hello.delivery.order.controller;


import hello.delivery.common.api.ApiResponse;
import hello.delivery.order.controller.response.OrderResponse;
import hello.delivery.order.domain.OrderCreate;
import hello.delivery.order.service.OrderService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/new")
    public ApiResponse<OrderResponse> order(@Valid @RequestParam OrderCreate request) {
        return ApiResponse.ok(OrderResponse.of(orderService.order(request)));
    }

    @GetMapping("/user")
    public ApiResponse<List<OrderResponse>> getOrdersByUserId(@RequestParam long userId) {
        return ApiResponse.ok(OrderResponse.of(orderService.findOrdersByUserId(userId)));
    }

}
