package hello.delivery.delivery.controller;

import hello.delivery.common.annotation.LoginUser;
import hello.delivery.common.api.ApiResponse;
import hello.delivery.delivery.controller.docs.DeliveryControllerDocs;
import hello.delivery.delivery.controller.port.DeliveryService;
import hello.delivery.delivery.controller.response.DeliveryResponse;
import hello.delivery.delivery.domain.Delivery;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/deliveries")
@RequiredArgsConstructor
public class DeliveryController implements DeliveryControllerDocs {

    private final DeliveryService deliveryService;

    @Override
    @PatchMapping("/{deliveryId}/start")
    public ApiResponse<DeliveryResponse> start(@LoginUser Long userId, @PathVariable Long deliveryId) {
        Delivery delivery = deliveryService.start(deliveryId);
        return ApiResponse.ok(DeliveryResponse.of(delivery));
    }

    @Override
    @PatchMapping("/{deliveryId}/complete")
    public ApiResponse<DeliveryResponse> complete(@LoginUser Long userId, @PathVariable Long deliveryId) {
        Delivery delivery = deliveryService.complete(deliveryId);
        return ApiResponse.ok(DeliveryResponse.of(delivery));
    }

    @Override
    @GetMapping("/{deliveryId}")
    public ApiResponse<DeliveryResponse> getDeliveryById(Long userId, @PathVariable Long deliveryId) {
        Delivery delivery = deliveryService.findById(deliveryId);
        return ApiResponse.ok(DeliveryResponse.of(delivery));
    }

    @Override
    @GetMapping("/order/{deliveryId}")
    public ApiResponse<DeliveryResponse> getOrderForDelivery(Long userId, @PathVariable Long deliveryId) {
        Delivery delivery = deliveryService.findByOrderId(deliveryId);
        return ApiResponse.ok(DeliveryResponse.of(delivery));
    }

}
