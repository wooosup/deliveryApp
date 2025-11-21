package hello.delivery.order.controller.docs;

import hello.delivery.common.annotation.LoginUser;
import hello.delivery.common.api.ApiResponse;
import hello.delivery.order.controller.response.OrderResponse;
import hello.delivery.order.domain.OrderCreate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "주문")
public interface OrderControllerDocs {

    @Operation(summary = "주문 생성", description = "새로운 주문을 생성합니다.")
    ApiResponse<OrderResponse> order(@Parameter(hidden = true) @LoginUser Long userId,
                                            @Valid @RequestBody OrderCreate request);

    @Operation(summary = "내 주문 조회", description = "로그인한 사용자의 모든 주문을 조회합니다.")
    ApiResponse<List<OrderResponse>> getMyOrders(@Parameter(hidden = true) @LoginUser Long userId);

}
